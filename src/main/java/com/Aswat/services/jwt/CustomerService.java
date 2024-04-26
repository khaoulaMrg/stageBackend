package com.Aswat.services.jwt;

import jdk.jfr.Category;

import java.io.IOException;
import com.Aswat.Dtos.CategoryDto;

public interface CustomerService {
    void postCategory(CategoryDto categoryDto) throws IOException;
}
