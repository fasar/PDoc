package com.openbee.test.bayesian

import org.junit._

@Test
class SimpleTokenizerTest {

    @Test
    def testOK() = {
      val tokenizer = new SimpleTokenizer()
      val res = tokenizer.tokenize("Test1 test2   test3 , test4. test5")
      val list = res.toList
      val size = list.size
      Assert.assertEquals(size, 5)
      Assert.assertEquals(list(3), "test4")
    }

}


