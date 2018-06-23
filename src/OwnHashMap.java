import javax.xml.bind.annotation.XmlType;

/**
 * 模拟一个HashMap的实现。
 */
public class OwnHashMap {
    //先定义HashMap中的变量
    //定义HashMap的默认容量
    private static final int DEFAULT_INIT_SIZE = 16;
    //定义HashMap的负载因子
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    //定义HashMap中元素的个数。
    private  int size;
    //定义HashMap的临界值
    private int threshold;
    //定义HashMap的扩容次数
    private int resize;

    private HashEntry[] table;
    //构造函数进行初始化。
    OwnHashMap(){
        table = new HashEntry[DEFAULT_INIT_SIZE];
        threshold = (int)(DEFAULT_INIT_SIZE * DEFAULT_LOAD_FACTOR);
        size = 0;
    }
    //根据key来计算其index，这个index表示这个key value 具体放在数组中的哪一个位置上。
    private int getIndex(Object key){
        int index = key.hashCode() % table.length - 1;
        return index;
    }
    //添加数据
    public void put(Object key,Object value){
        //判断key是否为null如果为null直接返回
        if(key == null){
            return;
        }
        int index = getIndex(key);
        HashEntry entry = table[index];
        //遍历判断即将添加的数据中key是不是存在了如果存在，直接将其覆盖即可。没有直接添加数据。
        while(entry != null){
            if(entry.getKey().hashCode() == key.hashCode() && entry.getKey() == entry.getKey() && entry.getKey().equals(key)){
                entry.setValue(value);
                return;
            }
            entry = entry.getNext();
        }
        addEntry(index,key,value);
    }
    //将数据添加到entry中
    public void addEntry(int index,Object key,Object value){
        //
        HashEntry entry = new HashEntry(key,value,table[index]);
        table[index] = entry;
        //判断size是否达到临界值，若已达到则进行扩容，将table的capacicy翻倍
        if (size++ >= threshold) {
            resize(table.length * 2);
        }
    }
    private void resize(int capacity) {
        if (capacity <= table.length) return;

        HashEntry[] newTable = new HashEntry[capacity];
        //遍历原table，将每个entry都重新计算hash放入newTable中
        for (int i = 0; i < table.length; i++) {
            HashEntry old = table[i];
            while (old != null) {
                HashEntry next = old.getNext();
                int index = getIndex(old.getKey());
                old.setNext(newTable[index]);
                newTable[index] = old;
                old = next;
            }
        }
        //用newTable替table
        table = newTable;
        //修改临界值
        threshold = (int) (table.length * DEFAULT_LOAD_FACTOR);
        resize++;
    }
    public Object get(Object key) {
        //这里简化处理，忽略null值
        if (key == null) return null;
        HashEntry entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    public HashEntry getEntry(Object key) {
        HashEntry entry = table[getIndex(key)];
        while (entry != null) {
            if (entry.getKey().hashCode() == key.hashCode() && (entry.getKey() == key || entry.getKey().equals(key))) {
                return entry;
            }
            entry = entry.getNext();
        }
        return null;
    }

    public void remove(Object key) {
        if (key == null) return;
        int index = getIndex(key);
        HashEntry pre = null;
        HashEntry entry = table[index];
        while (entry != null) {
            if (entry.getKey().hashCode() == key.hashCode() && (entry.getKey() == key || entry.getKey().equals(key))) {
                if (pre == null) table[index] = entry.getNext();
                else pre.setNext(entry.getNext());
                //如果成功找到并删除，修改size
                size--;
                return;
            }
            pre = entry;
            entry = entry.getNext();
        }
    }

    public boolean containsKey(Object key) {
        if (key == null) return false;
        return getEntry(key) != null;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        this.size = 0;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("size:%s capacity:%s resize:%s\n\n", size, table.length, resize));
        for (HashEntry entry : table) {
            while (entry != null) {
                sb.append(entry.getKey() + ":" + entry.getValue() + "\n");
                entry = entry.getNext();
            }
        }
        return sb.toString();
    }
}

/**
 * 这是一个封装了key value 以及entry的实体类
 */
class HashEntry{

    private  Object key;
    private  Object value;
    private HashEntry next;
    public HashEntry(Object key,Object value,HashEntry next){
        this.key = key;
        this.value = value;
        this.next = next;
    }
    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public HashEntry getNext() {
        return next;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setNext(HashEntry next) {
        this.next = next;
    }
}
