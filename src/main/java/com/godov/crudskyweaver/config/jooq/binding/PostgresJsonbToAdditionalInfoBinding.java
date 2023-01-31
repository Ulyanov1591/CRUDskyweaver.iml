package com.godov.crudskyweaver.config.jooq.binding;

import com.godov.crudskyweaver.config.jooq.converter.PostgresJsonbToAdditionalInfoConverter;
import com.godov.crudskyweaver.dto.user.AdditionalInfo;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

public class PostgresJsonbToAdditionalInfoBinding implements Binding<JSONB, AdditionalInfo> {

    @Override
    public Converter<JSONB, AdditionalInfo> converter() {
        return new PostgresJsonbToAdditionalInfoConverter();
    }

    @Override
    public void sql(BindingSQLContext<AdditionalInfo> ctx) throws SQLException {
        if(ctx.render().paramType() == ParamType.INLINED)
            ctx.render().visit(DSL.inline(ctx.convert(converter()).value())).sql("::jsonb");
        else{
            ctx.render().sql(ctx.variable()).sql("::jsonb");
        }
    }

    @Override
    public void register(BindingRegisterContext<AdditionalInfo> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<AdditionalInfo> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void set(BindingSetSQLOutputContext<AdditionalInfo> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetResultSetContext<AdditionalInfo> ctx) throws SQLException {
        ctx.convert(converter()).value(JSONB.valueOf(ctx.resultSet().getString(ctx.index())));
    }

    @Override
    public void get(BindingGetStatementContext<AdditionalInfo> ctx) throws SQLException {
        ctx.convert(converter()).value(JSONB.valueOf(ctx.statement().getString(ctx.index())));
    }

    @Override
    public void get(BindingGetSQLInputContext<AdditionalInfo> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
