package com.helpmeandyou.demo.Repository;

import com.helpmeandyou.demo.Model.Conto;

import java.util.List;

public interface ContoRepository {
    List<Conto> findAll();
}
