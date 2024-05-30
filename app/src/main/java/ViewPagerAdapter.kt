

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.news.CrimeFragment
import com.example.news.EnetertenmentFragment
import com.example.news.PoliticalFragment
import com.example.news.SportFragment
import com.example.news.TechnologyFragment
import com.example.news.TrendingFragment

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {



    var tabcount = behavior
    override fun getCount(): Int {
        return tabcount
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return TrendingFragment()
            }

            1 -> {
                return EnetertenmentFragment()
            }

            2 -> {
                return CrimeFragment()
            }

            3 -> {
                return PoliticalFragment()
            }

            4 -> {
                return SportFragment()
            }

            else -> {   //5
                return TechnologyFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {

        when (position) {
            0 -> {
                return "Trending"
            }

            1 -> {
                return "Enetertenment"
            }

            2 -> {
                return "Crime"
            }

            3 -> {
                return "Political"
            }

            4 -> {
                return "Sport"
            }

            else -> {   //5
                return "Technology"
            }
        }
    }

}



