package com.lpm.shop.server.imp;

import com.lpm.shop.entity.Menu;
import com.lpm.shop.mapper.MenuMapper;
import com.lpm.shop.server.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List buildMenuList() {
        List<Menu> all = menuMapper.findAll();
        List<Menu> menus = menuMapper.findAllByParentId(0);
        List alllist = new ArrayList();
        for (Menu menu : menus) {
            HashMap parentmap = new HashMap();
            List childlist = new ArrayList();
            List<Menu> child = getChild(menu.getId(),all);
            menu.setChild(child);
            if (menu.getParentId()==0){
                parentmap.put("id",menu.getId());
                parentmap.put("authName",menu.getName());
                parentmap.put("path",menu.getPath());
                for (Menu menu1 : child) {
                    HashMap childmap = new HashMap();
                    if (menu1.getParentId() == menu.getId()){
                        childmap.put("id",menu1.getId());
                        childmap.put("Name",menu1.getName());
                        childmap.put("path",menu1.getPath());
                    }
                    childlist.add(childmap);
                }
                parentmap.put("children",childlist);
                alllist.add(parentmap);
            }
        }
        return alllist;
    }

    public List getChild(int id, List<Menu> parentmenus) {
        List<Menu> childList = new ArrayList<>();
        for (Menu menu : parentmenus) {
            if (menu.getParentId() == id){
                childList.add(menu);
            }
        }

        for (Menu menu : childList) {
            menu.setChild(getChild(menu.getId(),parentmenus));
        }

        Collections.sort(childList,order());

        if (childList.size() == 0){
            return new ArrayList<>();
        }
        return childList;
    }

    public Comparator<Menu> order(){
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if(o1.getQueue() != o2.getQueue()){
                    return o1.getQueue() - o2.getQueue();
                }
                return 0;
            }
        };
        return comparator;
    }



}
