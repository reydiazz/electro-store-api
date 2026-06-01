package com.electro.store.api.domain.buys.web.controller;


import com.electro.store.api.domain.buys.service.SupplierService;
import com.electro.store.api.domain.buys.web.request.CreateSupplierRequest;
import com.electro.store.api.domain.buys.web.request.UpdateSupplierRequest;
import com.electro.store.api.domain.buys.web.response.SupplierResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suppliers")
public class SupplierController {

    private  final SupplierService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<Page<SupplierResponse>> findAll(Pageable pageable){
        Page<SupplierResponse> response = service.findAll(pageable);
        return  ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<SupplierResponse> create(@RequestBody @Valid CreateSupplierRequest request){
        SupplierResponse response = service.create(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<SupplierResponse> update(@PathVariable String code, @RequestBody @Valid UpdateSupplierRequest request){
        SupplierResponse response = service.update(code,request);
        return  ResponseEntity.ok(response);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code){
        service.delete(code);
        return  ResponseEntity.noContent().build();
    }

}
