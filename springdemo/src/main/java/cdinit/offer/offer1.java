package cdinit.offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class offer1 {
    public static void main(String[] args) {
        int[] arr = {2,3,1,0,2,5,3};
        List<Integer> numbers = findNumbers(arr);
        System.out.println(numbers);
    }

    static List<Integer> findNumbers(int[] args){
        List<Integer> list = new ArrayList<>();
        //具体算法
        for(int i=0;i<args.length;i++){
            // 查找下标为i的数字,看是否等于i
            // 如果相等,不做任何操作,
            // 如果不等,判断args[i]中的元素是否与替换的值相等,如果相等加入list,
            if(args[i] != i){
                int temp = args[i];
                int temp2 = args[temp];
                if(Objects.equals(temp2,temp)){
                    list.add(temp2);
                }else{
                    // 否则替换到下标为args[i]中去
                    args[i] = temp2;
                    args[temp] = temp;
                }
            }

        }

        return list;
    }
}
