package com.glyxybxhtxt;

import com.glyxybxhtxt.dataObject.Shy;
import com.glyxybxhtxt.service.EwmService;
import com.glyxybxhtxt.service.ShyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class GlyxybxhtxtApplicationTests {
   @Autowired
   private EwmService es;
   @Autowired
   private ShyService shyService;

   @Test
   void contextLoads() {
       System.out.println(es.selxxwz(6));
   }
   
   @Test
    public void test01() {
       List<Shy> selallqy = shyService.selallqy();
       for (Shy shy : selallqy) {
           System.out.println(shy.getXm());
       }
   }

}
