package com.zyc.baselibs.web.bootstrap;

public class TreeViewNodeForLazyLoad extends TreeViewNode {

	private static final long serialVersionUID = -5864408878380143413L;

	private boolean lazyLoad = true;

	public boolean isLazyLoad() {
		return lazyLoad;
	}

	public void setLazyLoad(boolean lazyLoad) {
		this.lazyLoad = lazyLoad;
	}
}
