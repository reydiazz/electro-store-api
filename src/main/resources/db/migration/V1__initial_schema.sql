CREATE TABLE people
(
    code        VARCHAR(20) PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    phone       VARCHAR(20) NULL,
    national_id VARCHAR(20) NULL
);

CREATE UNIQUE NONCLUSTERED INDEX UQ_people_phone 
    ON people(phone) WHERE phone IS NOT NULL;

CREATE UNIQUE NONCLUSTERED INDEX UQ_people_national_id 
    ON people(national_id) WHERE national_id IS NOT NULL;

CREATE TABLE employees
(
    code        VARCHAR(20) PRIMARY KEY,
    person_code VARCHAR(20) UNIQUE NOT NULL,
    position    VARCHAR(100)       NOT NULL,
    salary      DECIMAL(10, 2)     NOT NULL,
    FOREIGN KEY (person_code) REFERENCES people (code)
);

CREATE TABLE customers
(
    code        VARCHAR(20) PRIMARY KEY,
    person_code VARCHAR(20) UNIQUE NOT NULL,
    tax_id      VARCHAR(20) NULL,
    FOREIGN KEY (person_code) REFERENCES people (code)
);

CREATE UNIQUE NONCLUSTERED INDEX UQ_customers_tax_id 
    ON customers(tax_id) WHERE tax_id IS NOT NULL;

CREATE TABLE users
(
    code          VARCHAR(20) PRIMARY KEY,
    employee_code VARCHAR(20) UNIQUE NOT NULL,
    username      VARCHAR(50)        NOT NULL UNIQUE,
    password      VARCHAR(255)       NOT NULL,
    role          VARCHAR(50)        NOT NULL,
    FOREIGN KEY (employee_code) REFERENCES employees (code)
);


CREATE TABLE suppliers
(
    code       VARCHAR(20) PRIMARY KEY,
    tax_id     VARCHAR(20) NOT NULL UNIQUE,
    trade_name VARCHAR(150) NOT NULL,
    phone      VARCHAR(20) NOT NULL UNIQUE,
    legal_name VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE product_categories
(
    code VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE products
(
    code            VARCHAR(20) PRIMARY KEY,
    category_code   VARCHAR(20)    NOT NULL,
    name            VARCHAR(150)   NOT NULL,
    brand           VARCHAR(100)   NOT NULL,
    model           VARCHAR(100)   NOT NULL,
    sale_price      DECIMAL(10, 2) NOT NULL,
    stock           INT            NOT NULL DEFAULT 0,
    description     VARCHAR(255)   NULL,
    warranty_months INT            NOT NULL DEFAULT 0,
    FOREIGN KEY (category_code) REFERENCES product_categories (code)
);

CREATE TABLE sales
(
    code          VARCHAR(20) PRIMARY KEY,
    user_code     VARCHAR(20) NOT NULL,
    customer_code VARCHAR(20) NOT NULL,
    sale_date     DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_code) REFERENCES users (code),
    FOREIGN KEY (customer_code) REFERENCES customers (code)
);

CREATE TABLE sale_details
(
    code         VARCHAR(20) PRIMARY KEY,
    sale_code    VARCHAR(20)    NOT NULL,
    product_code VARCHAR(20)    NOT NULL,
    sale_price   DECIMAL(10, 2) NOT NULL,
    quantity     INT            NOT NULL,
    FOREIGN KEY (sale_code) REFERENCES sales (code),
    FOREIGN KEY (product_code) REFERENCES products (code)
);

CREATE TABLE purchases
(
    code          VARCHAR(20) PRIMARY KEY,
    user_code     VARCHAR(20) NOT NULL,
    supplier_code VARCHAR(20) NOT NULL,
    purchase_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_code) REFERENCES users (code),
    FOREIGN KEY (supplier_code) REFERENCES suppliers (code)
);

CREATE TABLE purchase_details
(
    code           VARCHAR(20) PRIMARY KEY,
    purchase_code  VARCHAR(20)    NOT NULL,
    product_code   VARCHAR(20)    NOT NULL,
    purchase_price DECIMAL(10, 2) NOT NULL,
    quantity       INT            NOT NULL,
    FOREIGN KEY (purchase_code) REFERENCES purchases (code),
    FOREIGN KEY (product_code) REFERENCES products (code)
);

CREATE TABLE inventory_guides
(
    code        VARCHAR(20) PRIMARY KEY,
    user_code   VARCHAR(20) NOT NULL,
    type        VARCHAR(10) NOT NULL CHECK (type IN ('ENTRY', 'EXIT')),
    reason      VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    guide_date  DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_code) REFERENCES users (code)
);

CREATE TABLE guide_details
(
    code         VARCHAR(20) PRIMARY KEY,
    guide_code   VARCHAR(20) NOT NULL,
    product_code VARCHAR(20) NOT NULL,
    quantity     INT         NOT NULL,
    FOREIGN KEY (guide_code) REFERENCES inventory_guides (code),
    FOREIGN KEY (product_code) REFERENCES products (code)
);
