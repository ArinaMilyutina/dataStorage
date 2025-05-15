package com.example.datastorage.mapper;

import com.example.datastorage.dto.user.RequestAuthUserDto;
import com.example.datastorage.dto.user.RequestRegUserDto;
import com.example.datastorage.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User regUserDtoToUser(RequestRegUserDto regUserDto);

    User loginUserDtoToUser(RequestAuthUserDto authUserDto);
}
