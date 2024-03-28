package com.kalambo.libraryapi.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePermissionDto {
    private UUID roleId;
    private List<UUID> addedPermissionsIds = new ArrayList<UUID>();
    private List<UUID> removedPermissionsIds = new ArrayList<UUID>();
}
