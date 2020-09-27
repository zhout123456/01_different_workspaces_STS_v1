package com.wisely.ch8_2_M2.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.wisely.ch8_2_M2.entity.bnUser;

@Service
public class testService {
	@Autowired
    private JdbcTemplate jdbcTemplate;
 
    public List getList(){
    	
//        String sql = "select user_id,user_phone,user_name,his_money from bn_user";
        String sql = "select a.\"userId\", a.\"userPhone\", a.\"userName\", a.\"hisMoney\" from BOOT.\"bn_user\" a";
        return (List) jdbcTemplate.query(sql, new RowMapper(){
 
            public bnUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                bnUser bnUser = new bnUser();
                bnUser.setUserId(rs.getString("userId"));
                bnUser.setUserPhone(rs.getString("userPhone"));
                bnUser.setUserName(rs.getString("userName"));
                bnUser.setHisMoney(rs.getDouble("hisMoney"));
                
                return bnUser;
            }
 
        });
    }

}
