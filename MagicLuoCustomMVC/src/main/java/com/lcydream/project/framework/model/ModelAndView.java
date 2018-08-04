package com.lcydream.project.framework.model;

import java.util.Map;

/**
 * ModelAndView
 * 视图
 *
 * @author Luo Chun Yun
 * @date 2018/7/22 11:04
 */
public class ModelAndView {

    /**
     * 视图名称，spring定义的是view是一个Object对象
     */
    private Object view;

    /**
     * model 的map数据 spring定义的是ModelMap，这是一个linkedMap
     */
    private Map<String, Object> model;

    public ModelAndView(Object view) {
        this.view = view;
    }

    public ModelAndView(Object view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }

    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
