package diffutil.application.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import diffutil.application.R

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.tvSimpleAdapter).setOnClickListener {
            findNavController(view).navigate(R.id.action_mainFragment_to_simpleAdapterFragment)
        }
        view.findViewById<View>(R.id.tvDiffUtilAdapter).setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_diffUtilAdapterFragment)
        }
        view.findViewById<View>(R.id.tvManuallyDiffUtilAdapter).setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_manuallyDiffUtilAdapterFragment)
        }
    }
}