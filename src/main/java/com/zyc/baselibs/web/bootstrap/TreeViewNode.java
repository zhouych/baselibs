package com.zyc.baselibs.web.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bootstrap-treeview的节点：bootstrap-treeview是一款基于bootstrap的jQuery多级列表树插件。
 * @author zhouyancheng
 *
 */
public class TreeViewNode implements java.io.Serializable {

	private static final long serialVersionUID = -4988165765140449625L;
	
	private String text; //列表树节点上的文本，通常是节点右边的小图标。
	private String icon; //列表树节点上的图标，通常是节点左边的图标。
	private String selectedIcon; //当某个节点被选择后显示的图标，通常是节点左边的图标。
	private String href; //结合全局enableLinks选项为列表树节点指定URL。
	private boolean selectable = true; //指定列表树的节点是否可选择。设置为false将使节点展开，并且不能被选择。
	private TreeViewNodeState state; //= new TreeViewNodeState(); //一个节点的初始状态。
	private String color; //节点的前景色，覆盖全局的前景色选项。
	private String backColor; //节点的背景色，覆盖全局的背景色选项。
	private List<String> tags = new ArrayList<String>(); //通过结合全局showTags选项来在列表树节点的右边添加额外的信息。
	private List<TreeViewNode> nodes = null; //子节点
	private Map<String, Object> attrs = new HashMap<String, Object>(); //其他额外的属性，追加到节点对应html元素的data-xxx上。
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getSelectedIcon() {
		return selectedIcon;
	}
	public void setSelectedIcon(String selectedIcon) {
		this.selectedIcon = selectedIcon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public boolean isSelectable() {
		return selectable;
	}
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	public TreeViewNodeState getState() {
		return state;
	}
	public void setState(TreeViewNodeState state) {
		this.state = state;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBackColor() {
		return backColor;
	}
	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<TreeViewNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<TreeViewNode> nodes) {
		this.nodes = nodes;
	}
	public Map<String, Object> getAttrs() {
		return attrs;
	}
}
