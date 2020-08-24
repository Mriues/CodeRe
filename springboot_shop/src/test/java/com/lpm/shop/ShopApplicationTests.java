package com.lpm.shop;

import com.lpm.shop.entity.*;
import com.lpm.shop.mapper.*;
import com.lpm.shop.server.imp.MenuServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
class ShopApplicationTests {

    @Autowired
    RoleUnionRightMapper unionRightMapper;

    @Autowired
    RightsMapper rightsMapper;

    @Autowired
    RolesMapper rolesMapper;

    @Autowired
    CategoriesMapper categoriesMapper;

    @Autowired
    AttributeMapper attributeMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    MenuServiceImpl menuService;


    @Test
    public void getArea() {
        List menus = menuService.buildMenuList();
        System.out.println(menus);
    }

    @Test
    public List getDate() {
        List<Report> area = reportMapper.getDate();
        List dateList = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        for (Report report : area) {
            dateList.add(dateFormat.format(report.getDate()));
        }
        return dateList;
    }

    @Test
    void goodsTest(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(date);
        long l = date.getTime() / 1000;
        System.out.println(l);
        //System.out.println(format);
    }

    @Test
    public void getAttributeById() {
        List<Attribute> attribute = attributeMapper.getAttributeById(1211, "many");
        for (Attribute attribute1 : attribute) {
            System.out.println(attribute1.getAttrVals());
        }
    }

    @Test
    void getCategoriesList() {
        List<Categories> categoriesList = categoriesMapper.getCategoriesList();
        List cateList = getZeroOfCate(categoriesList,3);
        System.out.println(cateList.size());
    }


    @Test
    private List getZeroOfCate(List<Categories> categoriesList,int type){
        List cateList = new ArrayList();
        for (Categories categories : categoriesList) {
            if (categories.getCatLevel() == 0){
                HashMap<String,Object> map = new HashMap<>();
                map.put("cat_id",categories.getCatId());
                map.put("cat_name",categories.getCatName());
                map.put("cat_pid",categories.getCatPid());
                map.put("cat_level",categories.getCatLevel());
                if (categories.getCatDeleted() == 0){
                    map.put("cat_deleted",false);
                }else {
                    map.put("cat_deleted",true);
                }
                if (type != 1){
                    map.put("children",getOneOfCate(categoriesList,categories.getCatId(),type));
                }
                cateList.add(map);
            }
        }
        return cateList;
    }

    @Test
    private List getOneOfCate(List<Categories> categoriesList, int catId,int type) {
        List oneList = new ArrayList();
        for (Categories categories : categoriesList) {
            if (categories.getCatLevel() == 1 && categories.getCatPid() == catId){
                HashMap<String,Object> map = new HashMap<>();
                map.put("cat_id",categories.getCatId());
                map.put("cat_name",categories.getCatName());
                map.put("cat_pid",categories.getCatPid());
                map.put("cat_level",categories.getCatLevel());
                if (categories.getCatDeleted() == 0){
                    map.put("cat_deleted",false);
                }else {
                    map.put("cat_deleted",true);
                }
                if (type != 2){
                    map.put("children",getTwoOfCate(categoriesList,categories.getCatId()));
                }
                oneList.add(map);
            }
        }
        return oneList;
    }

    @Test
    private List getTwoOfCate(List<Categories> categoriesList, int catId) {
        List twoList = new ArrayList();
        for (Categories categories : categoriesList) {
            if (categories.getCatLevel() == 2 && categories.getCatPid() == catId) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("cat_id", categories.getCatId());
                map.put("cat_name", categories.getCatName());
                map.put("cat_pid", categories.getCatPid());
                map.put("cat_level", categories.getCatLevel());
                if (categories.getCatDeleted() == 0){
                    map.put("cat_deleted",false);
                }else {
                    map.put("cat_deleted",true);
                }
                twoList.add(map);
            }
        }
        return twoList;
    }


    @Test
    void getRolesandRights(){
        List listAll = new ArrayList();
        List<RoleUnionRight> unionList = unionRightMapper.getRolesUnionRightsList();
        List<Role> roleList = rolesMapper.getRoleList();
        for (Role role : roleList) {
            HashMap<String,Object> roleMap = new HashMap<>();
            List oneList = new ArrayList();
            roleMap.put("id", role.getId());
            roleMap.put("roleName", role.getRoleName());
            roleMap.put("roleDesc", role.getRoleDesc());

            for (RoleUnionRight rights : unionList) {
                if (rights.getLevel().equals("0") && role.getId() == rights.getRoleId()){
                    HashMap<String,Object> oneMap = new HashMap<>();
                    List twoList = new ArrayList();
                    oneMap.put("id",rights.getRightId());
                    oneMap.put("authName",rights.getAuthName());
                    oneMap.put("path",rights.getPath());

                    for (RoleUnionRight rights1 : unionList) {
                        if (rights1.getLevel().equals("1") && role.getId() == rights1.getRoleId() && rights1.getPid() == rights.getRightId()){
                            HashMap<String,Object> twoMap = new HashMap<>();
                            if (rights1.getPid() == rights.getId()){
                                List threeList = new ArrayList();
                                twoMap.put("id",rights1.getRightId());
                                twoMap.put("authName",rights1.getAuthName());
                                twoMap.put("path",rights1.getPath());


                                for (RoleUnionRight rights2 : unionList) {
                                    if (rights2.getLevel().equals("2") && role.getId() == rights2.getRoleId() && rights2.getPid() == rights1.getRightId()){
                                        HashMap<String,Object> threeMap = new HashMap<>();
                                        if (rights2.getPid() == rights1.getId()){
                                            threeMap.put("id",rights2.getRightId());
                                            threeMap.put("authName",rights2.getAuthName());
                                            threeMap.put("path",rights2.getPath());
                                            threeList.add(threeMap);
                                        }
                                    }
                                }


                                twoMap.put("children",threeList);
                                twoList.add(twoMap);
                            }

                        }
                    }


                    roleMap.put("children",oneList);
                    listAll.add(roleMap);
                }
            }


            roleMap.put("children",oneList);
            listAll.add(roleMap);
        }
        System.out.println(listAll);


    }

}
