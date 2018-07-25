package ru.therealmone.spoStackMachine.collections.arraylist;

import ru.therealmone.spoStackMachine.collections.ArrayList;
import ru.therealmone.spoStackMachine.collections.arraylist.exceptions.IndexOutOfBoundsException;

public class ArrayListImpl implements ArrayList {
    private int size;
    private double[] data;
    private int pointer;

    private static final int DATA_INIT_SIZE = 10;

    public ArrayListImpl() {
        size = 0;
        data = new double[DATA_INIT_SIZE];
        pointer = 0;
    }

    @Override
    public void add(double value) {
        if(pointer == data.length) {
            resize();
        }

        data[pointer] = value;
        size++;
    }

    @Override
    public double get(int index) {
        if(index >= data.length || index < 0) {
            throw new IndexOutOfBoundsException(index, size);
        }

        return data[index];
    }

    @Override
    public void rewrite(int index, double value) {
        if(index >= data.length || index < 0) {
            throw new IndexOutOfBoundsException(index, size);
        }

        data[index] = value;
    }

    @Override
    public void remove(int index) {
        if(index >= data.length || index < 0) {
            throw new IndexOutOfBoundsException(index, size);
        }

        double[] tempData = new double[data.length - 1];

        if(index == 0) {
            System.arraycopy(data, 1, tempData, 0, data.length - 1);
        } else if (index == data.length - 1) {
            System.arraycopy(data, 0, tempData, 0, data.length - 1);
        } else {
            System.arraycopy(data, 0, tempData, 0, index);
            System.arraycopy(data, index + 1, tempData, index, data.length - index - 1);
        }

        data = tempData;
        size--;
        pointer--;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("ArrayList@").append(this.hashCode()).append(":\n");
        for (int i = 0; i < data.length; i++) {
            out.append("index = ")
                    .append(i)
                    .append(", value = ")
                    .append(data[i]);
        }

        return out.toString();
    }

    private void resize() {
        double[] tempData = new double[(int) (data.length * 1.2)];
        System.arraycopy(data, 0, tempData, 0, data.length);

        data = tempData;
    }
}

