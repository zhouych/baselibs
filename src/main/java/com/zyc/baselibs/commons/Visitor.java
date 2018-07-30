package com.zyc.baselibs.commons;

public interface Visitor<P, R> {
	R visit(P o);
}
