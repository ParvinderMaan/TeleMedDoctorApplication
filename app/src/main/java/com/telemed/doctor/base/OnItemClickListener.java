package com.telemed.doctor.base;

public interface OnItemClickListener<T> extends BaseRecyclerListener {
    /**
     * Item has been clicked.
     *
     * @param item object associated with the clicked item.
     */
    void onItemClicked(T item);
}