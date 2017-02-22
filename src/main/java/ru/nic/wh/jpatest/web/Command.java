package ru.nic.wh.jpatest.web;

public interface Command<E> {

	E execute();
}
