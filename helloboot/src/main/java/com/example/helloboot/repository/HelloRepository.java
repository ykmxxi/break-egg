package com.example.helloboot.repository;

import com.example.helloboot.Hello;

public interface HelloRepository {

    Hello findHello(String name);

    void increaseCount(String name);

    // 간단한 데이터 조작 기능이라 Repository에서 구현
    // 원래는 Domain Object or Service 코드에서 진행
    default int countOf(String name) {
        Hello hello = findHello(name);
        if (hello == null) {
            return 0;
        }
        return hello.getCount();
    }

}
