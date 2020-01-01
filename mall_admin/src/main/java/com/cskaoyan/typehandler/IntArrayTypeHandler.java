package com.cskaoyan.typehandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Integer[].class)
public class IntArrayTypeHandler implements TypeHandler<Integer[]> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Integer[] integers, JdbcType jdbcType) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = null;
        try{
            s = objectMapper.writeValueAsString(integers);
        }catch (Exception e){
            e.printStackTrace();
        }
        preparedStatement.setString(i,s);
    }

    @Override
    public Integer[] getResult(ResultSet resultSet, String s) throws SQLException {
        String result = resultSet.getString(s);
        return transfer(result);
    }

    @Override
    public Integer[] getResult(ResultSet resultSet, int i) throws SQLException {
        String result = resultSet.getString(i);
        return transfer(result);
    }

    @Override
    public Integer[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String result = callableStatement.getString(i);
        return transfer(result);
    }

    private Integer[] transfer(String result){
        ObjectMapper objectMapper = new ObjectMapper();
        Integer[] integers = new Integer[0];
        try{
            integers = objectMapper.readValue(result,Integer[].class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return integers;
    }
}
