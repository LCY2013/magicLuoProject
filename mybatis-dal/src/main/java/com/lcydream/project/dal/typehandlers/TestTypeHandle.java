package com.lcydream.project.dal.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Note that MyBatis does not introspect upon the database metadata to determine the type,
// so you must specify that it’s a VARCHAR field in the parameter
// and result mappings to hook in the correct type handler.
// This is due to the fact that MyBatis is unaware of the data type until the statement is executed.
@MappedJdbcTypes(JdbcType.VARCHAR)
public class TestTypeHandle extends BaseTypeHandler<String> {
    public TestTypeHandle() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter+"with handlers");
    }
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }


}
