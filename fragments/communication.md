# LifeCycle

Fragment lifecycle is similar to Activity lifecycle

* Lifecycle callbacks define how Fragment behaves in each state
  * States
    * Active\(or Resumed\)
    * Paused
    * Stopped

<table>
  <thead>
    <tr>
      <th style="text-align:left"><b>Activity state</b>
      </th>
      <th style="text-align:left"><b>Fragment callbacks triggered</b>
      </th>
      <th style="text-align:left"><b>Fragment lifecycle</b>
      </th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="text-align:left"><b>Created</b>
      </td>
      <td style="text-align:left">
        <ul>
          <li> <a href="https://developer.android.com/reference/android/app/Fragment.html#onAttach(android.content.Context)"><b><code>onAttach()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onCreate(android.os.Bundle)"><b><code>onCreate()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onCreateView(android.view.LayoutInflater,%20android.view.ViewGroup,%20android.os.Bundle)"><b><code>onCreateView()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onActivityCreated(android.os.Bundle)"><b><code>onActivityCreated()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
        </ul>
      </td>
      <td style="text-align:left">Fragment is added and its layout is inflated</td>
    </tr>
    <tr>
      <td style="text-align:left"><b>Started</b>
      </td>
      <td style="text-align:left">
        <ul>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onStart()"><b><code>onStart()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
        </ul>
      </td>
      <td style="text-align:left">Fragment is active and visible</td>
    </tr>
    <tr>
      <td style="text-align:left"><b>Resumed</b>
      </td>
      <td style="text-align:left">
        <ul>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onResume()"><b><code>onResume()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
        </ul>
      </td>
      <td style="text-align:left">Fragment is active and ready for user interaction</td>
    </tr>
    <tr>
      <td style="text-align:left"><b>Paused</b>
      </td>
      <td style="text-align:left">
        <ul>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onPause()"><b><code>onPause()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
        </ul>
      </td>
      <td style="text-align:left">Fragment is paused because Activity is paused</td>
    </tr>
    <tr>
      <td style="text-align:left"><b>Stopped</b>
      </td>
      <td style="text-align:left">
        <ul>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onStop()"><b><code>onStop()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
        </ul>
      </td>
      <td style="text-align:left">Fragment is stopped and no longer visible</td>
    </tr>
    <tr>
      <td style="text-align:left"><b>Destroyed</b>
      </td>
      <td style="text-align:left">
        <ul>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onDestroyView()"><b><code>onDestroyView()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onDestroy()"><b><code>onDestroy()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
          <li>&lt;code&gt;&lt;/code&gt;<a href="https://developer.android.com/reference/android/app/Fragment.html#onDetach()"><b><code>onDetach()</code></b></a>&lt;code&gt;&lt;/code&gt;</li>
        </ul>
      </td>
      <td style="text-align:left">Fragment is destroyed</td>
    </tr>
  </tbody>
</table>

**@Override** [**`onCreate(Bundle savedInstanceState)`**](https://developer.android.com/reference/android/app/Fragment.html#onCreate%28android.os.Bundle%29)**:** 

* System calls `onCreate()` when the Fragment is created
* Initialize Fragment components and variables \(preserved if the Fragment is paused and resumed\)
* Always include `super.onCreate(savedInstanceState)` in lifecycle callbacks

|  |  |
| :--- | :--- |
| \`\`[**`onAttach()`**](https://developer.android.com/reference/android/app/Fragment.html#onAttach%28android.content.Context%29)\`\` | Called when Fragment is first attached to Activity |
| \`\`[**`onPause()`**](https://developer.android.com/reference/android/app/Fragment.html#onPause%28%29) | Called when Activity is paused |
| [**`onResume()`**](https://developer.android.com/reference/android/app/Fragment.html#onResume%28%29) | Called by Activity to resume a visible Fragment  |
| \`\`[**`onActivityCreated()`**](https://developer.android.com/reference/android/app/Fragment.html#onActivityCreated%28android.os.Bundle%29)**\`\`** | Called when Activity `onCreate()` method has returned |
| \`\`[**`onDestroyView()`**](https://developer.android.com/reference/android/app/Fragment.html#onDestroyView%28%29)\`\` | Called when View previously created by `onCreateView()` is detached from Fragment |



