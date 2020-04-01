package cdinit.hspjava;

public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);
        arrayQueue.add(1);
        arrayQueue.add(2);
        arrayQueue.add(3);
        arrayQueue.add(4);
        arrayQueue.list();
        int a = arrayQueue.get();
        System.out.println(a);
        a = arrayQueue.get();
        System.out.println(a);
        try {
            arrayQueue.get();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        arrayQueue.add(1);
        arrayQueue.add(2);
        arrayQueue.list();
        arrayQueue.add(3);
        arrayQueue.add(4);
        a = arrayQueue.get();
        System.out.println(a);
        a = arrayQueue.get();
        System.out.println(a);
        try {
            arrayQueue.get();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}

class ArrayQueue{
    private int maxSize;
    private int front;
    private int rear;
    private int[] arr;

    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new int[maxSize];
        front = 0;
        rear = 0;
    }

    //判断队列是否满
    public boolean isFull(){
        // 当满了(会空出一个元素的位置)的时候，read不会再往后移动
        return (rear + 1) % maxSize == front;
    }

    //判断队列是空
    public boolean isEmpty(){
        // 当满了(会空出一个元素的位置)的时候，read不会再往后移动
        return rear == front;
    }

    public void add(int n){
        // 判断队列是否满
        if(isFull()){
            System.out.println("队列已经满了！");
            return;
        }
        arr[rear] = n;
        rear = (rear + 1) % maxSize;
    }

    public int get(){
        // 判断队列是否为空
        if(isEmpty()){
            throw new RuntimeException("队列为空!");
        }
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    //当前队列的有效个数
    public int size(){
        return (rear + maxSize - front) % maxSize;
    }

    //显示所有的数据
    public void list(){
        if(isEmpty()){
            System.out.println("队列为空!");
            return;
        }
        for(int i=front; i < front + size(); i++){
            System.out.printf("arr[%d]=%d\n",i%maxSize,arr[i%maxSize]);
        }

    }
}
