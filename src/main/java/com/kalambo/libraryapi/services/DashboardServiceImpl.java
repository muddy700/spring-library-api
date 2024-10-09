package com.kalambo.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.repositories.BookRepository;
import com.kalambo.libraryapi.repositories.PermissionRepository;
import com.kalambo.libraryapi.repositories.RoleRepository;
import com.kalambo.libraryapi.repositories.UserRepository;
import com.kalambo.libraryapi.responses.IAdminSummary;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public IAdminSummary getAdminSummary() {
        IAdminSummary summary = new IAdminSummary().setUsers(userRepository.count()).setBooks(bookRepository.count())
                .setRoles(roleRepository.count()).setPermissions(permissionRepository.count());

        return summary;
    }
}
