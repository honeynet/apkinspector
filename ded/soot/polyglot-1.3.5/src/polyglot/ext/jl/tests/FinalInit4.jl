public class FinalInit4 {
   final int i;

    FinalInit4() {
	i = 2;
  	int k=i;
	for (int j = 0; j < 5; j++) {
  	    k = k+1;
  	}

  	while (k > i) {
  	    k --;
  	}
    }

}
