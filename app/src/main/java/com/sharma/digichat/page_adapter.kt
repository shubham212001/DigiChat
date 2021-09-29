package com.sharma.digichat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class page_adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return chats()
            }
            1 -> {
                return people()
            }
            else -> {
                return chats()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Chats"
            }
            1 -> {
                return "People"
            }

        }
        return super.getPageTitle(position)
    }

}