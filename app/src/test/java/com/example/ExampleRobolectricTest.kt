package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("TBSM AFGO", appName)
  }

  @Test
  fun `verify engine cc calculation`() {
    val result = com.example.calculator.EngineCalculatorEngine.calculateDisplacement(50.0, 55.6, 1)
    org.junit.Assert.assertTrue(result.second > 109.0 && result.second < 110.0)
  }
}
