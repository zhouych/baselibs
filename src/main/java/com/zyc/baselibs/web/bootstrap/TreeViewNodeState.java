package com.zyc.baselibs.web.bootstrap;

public class TreeViewNodeState {
	
	private boolean checked = false; //默认值false，指示一个节点是否处于checked状态，用一个checkbox图标表示。
	private boolean disabled = false; //默认值false，指示一个节点是否处于disabled状态。（不是selectable，expandable或checkable）
	private boolean expanded = false; //默认值false，指示一个节点是否处于展开状态。
	private boolean selected = false; //默认值false，指示一个节点是否可以被选择。
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
