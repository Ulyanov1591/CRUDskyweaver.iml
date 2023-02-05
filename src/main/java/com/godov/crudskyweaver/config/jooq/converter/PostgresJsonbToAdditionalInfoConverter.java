package com.godov.crudskyweaver.config.jooq.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godov.crudskyweaver.dto.user.AdditionalInfo;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Converter;
import org.jooq.JSONB;

@Slf4j
public class PostgresJsonbToAdditionalInfoConverter implements Converter<JSONB, AdditionalInfo> {
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private static final JSONB EMPTY_JSONB = JSONB.valueOf("");
    private static final AdditionalInfo EMPTY_ADDITIONAL_INFO = new AdditionalInfo();

    @Override
    public AdditionalInfo from(JSONB jsonb) {
        if(jsonb == null){
            return EMPTY_ADDITIONAL_INFO;
        }
        try{
            return objectMapper.readValue(jsonb.data(), AdditionalInfo.class);
        } catch (JsonProcessingException e) {
            log.warn("Error while parsing AdditionalInfo from DB. Exception: [{}]", e.getMessage());
            return EMPTY_ADDITIONAL_INFO;
        }
    }

    @Override
    public JSONB to(AdditionalInfo values) {
        if(values == null){
            return EMPTY_JSONB;
        }
        return getJsonb(values);
    }

    @Override
    public Class<JSONB> fromType() {
        return JSONB.class;
    }

    @Override
    public Class<AdditionalInfo> toType() {
        return AdditionalInfo.class;
    }

    private JSONB getJsonb(AdditionalInfo value) {
        try{
            return JSONB.valueOf(objectMapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return EMPTY_JSONB;
    }
}
