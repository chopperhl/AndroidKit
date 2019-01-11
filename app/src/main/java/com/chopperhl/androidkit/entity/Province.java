package com.chopperhl.androidkit.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Author chopperhl
 * Date 10/10/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class Province implements Serializable {

    /**
     * name : 北京
     * code : 110000
     * sub : [{"name":"北京市","code":"110000","sub":[{"name":"东城区","code":"110101"},{"name":"西城区","code":"110102"},{"name":"朝阳区","code":"110105"},{"name":"丰台区","code":"110106"},{"name":"石景山区","code":"110107"},{"name":"海淀区","code":"110108"},{"name":"门头沟区","code":"110109"},{"name":"房山区","code":"110111"},{"name":"通州区","code":"110112"},{"name":"顺义区","code":"110113"},{"name":"昌平区","code":"110114"},{"name":"大兴区","code":"110115"},{"name":"怀柔区","code":"110116"},{"name":"平谷区","code":"110117"},{"name":"密云县","code":"110228"},{"name":"延庆县","code":"110229"}]}]
     */

    public String name;
    public String code;
    public List<City> sub;

    @Override
    public String toString() {
        return name;
    }

    public static class City {
        /**
         * name : 北京市
         * code : 110000
         * sub : [{"name":"东城区","code":"110101"},{"name":"西城区","code":"110102"},{"name":"朝阳区","code":"110105"},{"name":"丰台区","code":"110106"},{"name":"石景山区","code":"110107"},{"name":"海淀区","code":"110108"},{"name":"门头沟区","code":"110109"},{"name":"房山区","code":"110111"},{"name":"通州区","code":"110112"},{"name":"顺义区","code":"110113"},{"name":"昌平区","code":"110114"},{"name":"大兴区","code":"110115"},{"name":"怀柔区","code":"110116"},{"name":"平谷区","code":"110117"},{"name":"密云县","code":"110228"},{"name":"延庆县","code":"110229"}]
         */

        public String name;
        public String code;
        public List<Area> sub;

        @Override
        public String toString() {
            return name;
        }

        public static class Area {
            /**
             * name : 东城区
             * code : 110101
             */

            public String name;
            public String code;

            public static Area empty() {
                Area area = new Area();
                area.name = "";
                area.code = "";
                return area;
            }

            @Override
            public String toString() {
                return name;
            }
        }
    }
}
