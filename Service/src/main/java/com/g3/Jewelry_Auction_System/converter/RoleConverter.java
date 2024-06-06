package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.ERole;
import com.g3.Jewelry_Auction_System.payload.DTO.RoleDTO;
import com.g3.Jewelry_Auction_System.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {
    public Role toEntity(RoleDTO dto){
        if (dto==null) return null;
        Role role = new Role();
        role.setRoleId(dto.getRoleId());
        role.setRoleName(ERole.valueOf(dto.getRoleName()));
        return role;
    }

    public RoleDTO toDTO(Role role){
        if (role==null) return null;
        RoleDTO dto = new RoleDTO();
        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName().name());
        return dto;
    }
}
