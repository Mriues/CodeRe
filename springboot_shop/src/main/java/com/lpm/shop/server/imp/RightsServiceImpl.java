package com.lpm.shop.server.imp;

import com.lpm.shop.entity.Rights;
import com.lpm.shop.mapper.RightsMapper;
import com.lpm.shop.server.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RightsServiceImpl implements RightsService {

    @Autowired
    RightsMapper rightsMapper;


    @Override
    public List getRightsList() {
        List list = new ArrayList();
        List<Rights> all = rightsMapper.findAll();
        for (Rights rights : all) {
            HashMap<String, Object> datamap = new HashMap<>();
            datamap.put("id", rights.getId());
            datamap.put("authName", rights.getAuthName());
            datamap.put("level", rights.getLevel());
            datamap.put("path", rights.getPath());
            datamap.put("pid", rights.getPid());
            list.add(datamap);
        }
        return list;
    }

    @Override
    public List getRightsTree() {
        List<Rights> all = rightsMapper.findAll();
        List allList = new ArrayList();
        for (Rights rights : all) {
            if (rights.getLevel().equals("0")) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", rights.getId());
                map.put("authName", rights.getAuthName());
                map.put("level", rights.getLevel());
                map.put("path", rights.getPath());
                map.put("pid", rights.getPid());
                map.put("children",getOneOfRight(all,rights.getId()));
                allList.add(map);
            }
        }
        return allList;

    }

    private List getOneOfRight(List<Rights> rightsList,int id) {
        List oneList = new ArrayList();
        for (Rights rights : rightsList) {
            if (rights.getLevel().equals("1") && rights.getPid() == id){
                HashMap<String,Object> map = new HashMap<>();
                map.put("id", rights.getId());
                map.put("authName", rights.getAuthName());
                map.put("level", rights.getLevel());
                map.put("path", rights.getPath());
                map.put("pid", rights.getPid());
                map.put("children",getTwoOfRight(rightsList,rights.getId()));
                oneList.add(map);
            }
        }
        return oneList;
    }

    private List getTwoOfRight(List<Rights> rightsList,int id){
        List twoList = new ArrayList();
        for (Rights rights : rightsList) {
            if (rights.getLevel().equals("2") && rights.getPid() == id){
                HashMap<String,Object> map = new HashMap<>();
                map.put("id", rights.getId());
                map.put("authName", rights.getAuthName());
                map.put("level", rights.getLevel());
                map.put("path", rights.getPath());
                map.put("pid", rights.getPid());
                twoList.add(map);
            }
        }
        return twoList;
    }

}
