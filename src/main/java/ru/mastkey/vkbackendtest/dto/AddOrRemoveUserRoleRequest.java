package ru.mastkey.vkbackendtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrRemoveUserRoleRequest {
    private String username;
	private String roleName;
}
