package cdinit.hspjava;

import java.util.Stack;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        HeroNode h1 = new HeroNode(1, "hero1");
        HeroNode h3 = new HeroNode(3, "hero3");
        HeroNode h2 = new HeroNode(2, "hero2");
        HeroNode h4 = new HeroNode(4, "hero4");
        singleLinkedList.add(h1);
        singleLinkedList.add(h3);
        singleLinkedList.add(h2);
        singleLinkedList.add(h4);
        singleLinkedList.del(2);
        singleLinkedList.del(5);
        singleLinkedList.list();
        System.out.println("======");
        singleLinkedList.del(1);
        singleLinkedList.del(3);
//        singleLinkedList.del(4);
        singleLinkedList.list();
        System.out.println("======");
        System.out.println("个数 --> " + singleLinkedList.getLength());

        System.out.println("======");
        singleLinkedList.update(new HeroNode(1, "hero111"));
        singleLinkedList.update(new HeroNode(4, "hero444"));
        singleLinkedList.update(new HeroNode(5, "hero444"));
        singleLinkedList.list();
        System.out.println("======");
        SingleLinkedList singleLinkedList2 = new SingleLinkedList();
        singleLinkedList2.addOrderBy(new HeroNode(1, "hero111"));
        singleLinkedList2.addOrderBy(new HeroNode(3, "hero111"));
        singleLinkedList2.addOrderBy(new HeroNode(2, "hero111"));
        singleLinkedList2.addOrderBy(new HeroNode(4, "hero111"));
        singleLinkedList2.addOrderBy(new HeroNode(4, "hero111"));
        singleLinkedList2.list();
        System.out.println("======");
        System.out.println("个数 --> " + singleLinkedList2.getLength());
        System.out.println("======");
        System.out.println("反转 --> ");
        singleLinkedList2.reverse().list();
        System.out.println("======");
        singleLinkedList2.reversePrint();
        System.out.println("======");
        System.out.println(singleLinkedList2.findLastNode(1));
        System.out.println(singleLinkedList2.findLastNode(2));
        System.out.println(singleLinkedList2.findLastNode(0));
        System.out.println(singleLinkedList2.findLastNode(5));
        System.out.println("======");
        System.out.println(singleLinkedList2.findNode(1));
        System.out.println(singleLinkedList2.findNode(2));
        System.out.println(singleLinkedList2.findNode(0));
        System.out.println(singleLinkedList2.findNode(5));
        System.out.println("======");
    }


}

class SingleLinkedList {
    private HeroNode head = new HeroNode(0, "");

    //添加节点
    public void add(HeroNode heroNode) {
        HeroNode temp = head;
        while (true) {
            if (temp.getNext() == null) {
                break;
            }
            temp = temp.getNext();
        }
        temp.setNext(heroNode);
    }

    //显示所有节点
    public void list() {
        HeroNode temp = head.getNext();
        if (head.getNext() == null) {
            System.out.println("链表为空！");
            return;
        }
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp.toString());
            temp = temp.getNext();
        }
    }

    //排序节点
    public void addOrderBy(HeroNode heroNode) {
        boolean flag = false;
        HeroNode temp = head;
        while (true) {
            if (temp.getNext() == null) {
                break;
            }
            if (temp.getNext().getNo() == heroNode.getNo()) {
                flag = true;
                break;
            }
            if (temp.getNext().getNo() > heroNode.getNo()) {
                break;
            }
            temp = temp.getNext();
        }

        if (flag) {
            System.out.println("该节点已经存在~");
        } else {
            heroNode.setNext(temp.getNext());
            temp.setNext(heroNode);
        }
    }


    //修改节点
    public void update(HeroNode newHeroNode) {
        if (head.getNext() == null) {
            System.out.println("链表为空！");
            return;
        }
        HeroNode temp = head.getNext();
        while (true) {
            if (temp == null) {
                System.out.println("没有找到~");
                break;
            }
            if (temp.getNo() == newHeroNode.getNo()) {
                temp.setName(newHeroNode.getName());
                break;
            }
            temp = temp.getNext();
        }

    }

    //删除节点
    public void del(int no) {
        if (head.getNext() == null) {
            System.out.println("链表为空");
            return;
        }

        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.getNext() == null) {
                break;
            }
            if (temp.getNext().getNo() == no) {
                flag = true;
                break;
            }
            temp = temp.getNext();
        }

        if (flag) {
            temp.setNext(temp.getNext().getNext());
        } else {
            System.out.println("删除失败。没有找到节点");
        }
    }

    //求单链表中的有效节点的个数
    public int getLength() {
        if (head.getNext() == null) {
            return 0;
        }
        int num = 0;
        HeroNode temp = head.getNext();
        while (true) {
            if (temp == null) {
                break;
            } else {
                num++;
            }
            temp = temp.getNext();
        }
        return num;
    }

    // 查找第J个节点
    public HeroNode findNode(int index){
        int length = getLength();
        if(head.getNext() == null || index <= 0 || index > length) return null;
        HeroNode cur = head.getNext();
        for(int i=1;i<index;i++){
            cur = cur.getNext();
        }
        return cur;
    }

    // 查找倒数第K个节点
    public HeroNode findLastNode(int index){
        int length = getLength();
        return findNode(length - index + 1);
    }

    // 链表反转
    public SingleLinkedList reverse(){
        //从尾到头取链表的节点放在最前面
        HeroNode cur = head.getNext();
        HeroNode next = null;
        HeroNode reverseHead = new HeroNode(0,"");
        while (cur != null){
            next = cur.getNext();
            cur.setNext(reverseHead.getNext());
            reverseHead.setNext(cur);
            cur = next;
        }
        this.head.setNext(reverseHead.getNext());
        return this;
    }

    //从尾到头打印链表 方式1：反转再打印  方式2：利用栈
    public void reversePrint(){
        if(head.getNext() == null){
            return ;
        }
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.getNext();
        while (cur != null){
            stack.push(cur);
            cur = cur.getNext();
        }
        while (stack.size() > 0){
            System.out.println(stack.pop());
        }
    }

}

class HeroNode {
    private int no;
    private String name;
    private HeroNode next;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getNext() {
        return next;
    }

    public void setNext(HeroNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
