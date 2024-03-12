package ru.mastkey.vkbackendtest.admin.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.mastkey.vkbackendtest.repositories.RoleRepository;
import ru.mastkey.vkbackendtest.repositories.UserRepository;

class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AdminService adminService;


}