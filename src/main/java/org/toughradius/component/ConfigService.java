package org.toughradius.component;

import org.apache.ibatis.annotations.Param;
import org.toughradius.entity.Config;
import org.toughradius.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {

    public final static String RADIUS_MODULE = "radius";
    public final static String RADIUS_IGNORE_PASSWORD = "RADIUS_IGNORE_PASSWORD";

    public final static String SYSTEM_MODULE = "system";
    public final static String SYSTEM_USERNAME = "SYSTEM_USERNAME";
    public final static String SYSTEM_USERPWD = "SYSTEM_USERPWD";

    @Autowired
    private ConfigMapper configMapper;

    public Config findConfig(String module, String name){
        return configMapper.findConfig(module,name);
    }

    public String getStringValue(String module, String name){
        Config cfg = configMapper.findConfig(module,name);
        if(cfg!=null){
            return cfg.getValue();
        }
        return null;
    }

    public Integer getInterimTimes(){
        return configMapper.getInterimTimes();
    }

    public Integer getIsCheckPwd(){
        return configMapper.getIsCheckPwd();
    }

    public void insertConfig(Config config){
        configMapper.insertConfig(config);
    }

    public void updateConfig(Config config){
        configMapper.updateConfig(config);
    }

    public void deleteById(Integer id){
        configMapper.deleteById(id);
    }

    public void deleteConfig(@Param(value = "type") String type, @Param(value = "name") String name){
        configMapper.deleteConfig(type,name);
    }

    public List<Config> queryForList(@Param(value = "type") String type){
        return configMapper.queryForList(type);
    }

}