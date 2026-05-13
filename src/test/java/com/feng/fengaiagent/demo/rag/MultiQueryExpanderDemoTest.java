package com.feng.fengaiagent.demo.rag;

import com.feng.fengaiagent.demo.rag.MultiQueryExpanderDemo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.rag.Query;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MultiQueryExpanderDemoTest {

    @Resource
    private MultiQueryExpanderDemo multiQueryExpanderDemo;

    @Test
    void expand() {
        List<Query> queries = multiQueryExpanderDemo.expand("啥是风风儿啊啊啊啊啊啊？");
        Assertions.assertNotNull(queries);
    }
}
