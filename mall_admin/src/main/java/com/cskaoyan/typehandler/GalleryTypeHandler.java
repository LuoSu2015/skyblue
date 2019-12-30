package com.cskaoyan.typehandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(String[].class)
public class GalleryTypeHandler implements TypeHandler<String[]> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, String[] strings, JdbcType jdbcType) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = null;
        try{
            s = objectMapper.writeValueAsString(strings);
        }catch (Exception e){
            e.printStackTrace();
        }
        preparedStatement.setString(i,s);
    }

    @Override
    public String[] getResult(ResultSet resultSet, String s) throws SQLException {
        String result = resultSet.getString(s);
        return transfer(result);
    }

    @Override
    public String[] getResult(ResultSet resultSet, int i) throws SQLException {
        String result = resultSet.getString(i);
        return transfer(result);
    }

    @Override
    public String[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String result = callableStatement.getString(i);
        return transfer(result);
    }

    private String[] transfer(String result){
        ObjectMapper objectMapper = new ObjectMapper();
        String[] strings = new String[0];
        try{
            strings = objectMapper.readValue(result,String[].class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return strings;
    }
}
