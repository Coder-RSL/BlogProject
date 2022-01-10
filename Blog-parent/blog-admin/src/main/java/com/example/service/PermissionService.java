package com.example.service;

import com.example.vo.Result;
import com.example.vo.params.PageParams;
import com.example.vo.pojo.Permission;
import org.springframework.stereotype.Service;

@Service
public interface PermissionService {
    Result listPermission(PageParams pageParams);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
