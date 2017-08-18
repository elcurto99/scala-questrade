package elcurto99.scalaquestrade.models

/**
  *
  * @see http://www.questrade.com/api/documentation/rest-operations/enumerations/enumerations
  */

object Currency extends Enumeration {
  type Currency = Value
  val USD, CAD = Value
}

object ListingExchange extends Enumeration {
  type ListingExchange = Value
  val TSX, TSXV, CNSX, MX, NASDAQ, NYSE, AMEX, ARCA, OPRA, PinkSheets, OTCBB = Value
}

object AccountType extends Enumeration {
  type AccountType = Value
  val Cash, Margin, TFSA, RRSP, SRRSP, LRRSP, LIRA, LIF, RIF, SRIF, LRIF, RRIF, PRIF, RESP, FRESP = Value

  def isRegistered(accountType: AccountType): Boolean = {
    accountType match {
      case TFSA | RRSP | SRRSP | LRRSP | RRIF | RESP | FRESP => true
      case _ => false
    }
  }
}

object ClientAccountType extends Enumeration {
  type ClientAccountType = Value
  val Individual = Value("Individual")
  val Joint = Value("Joint")
  val InformalTrust = Value("Informal Trust")
  val Corporation = Value("Corporation")
  val InvestmentClub = Value("Investment Club")
  val FormalTrust = Value("Formal Trust")
  val Partnership = Value("Partnership")
  val SoleProprietorship = Value("Sole Proprietorship")
  val Family = Value("Family")
  val JointAndInformalTrust = Value("Joint and Informal Trust")
  val Institution = Value("Institution")
}

object AccountStatus extends Enumeration {
  type AccountStatus = Value
  val Active = Value("Active")
  val SuspendedClosed = Value("Suspended (Closed)")
  val SuspendedViewOnly = Value("Suspended (View Only)")
  val LiquidateOnly = Value("Liquidate Only")
  val Closed = Value("Closed")
}

object TickType extends Enumeration {
  type TickType = Value
  val Up, Down, Equal = Value
}

object OptionType extends Enumeration {
  type OptionType = Value
  val Call, Put = Value
}

object OptionDurationType extends Enumeration {
  type OptionDurationType = Value
  val Weekly, Monthly, Quarterly, LEAP = Value
}

object OptionExerciseType extends Enumeration {
  type OptionExerciseType = Value
  val American, European = Value
}

object SecurityType extends Enumeration {
  type SecurityType = Value
  val Stock, Option, Bond, Right, Gold, MutualFund, Index = Value
}

object OrderStateFilterType extends Enumeration {
  type OrderStateFilterType = Value
  val All, Open, Closed = Value
}

object OrderAction extends Enumeration {
  type OrderAction = Value
  val Buy, Sell = Value
}

object OrderSide extends Enumeration {
  type OrderSide = Value
  val Buy, Sell, Short, Cov, BTO, STC, STO, BTC = Value
}

object OrderType extends Enumeration {
  type OrderType = Value
  val Market, Limit, Stop, StopLimit, TrailStopInPercentage, TrailStopInDollar, TrailStopLimitInPercentage, TrailStopLimitInDollar, LimitOnOpen, LimitOnClose = Value
}

object OrderTimeInForce extends Enumeration {
  type OrderTimeInForce = Value
  val Day, GoodTillCanceled, GoodTillExtendedDay, GoodTillDate, ImmediateOrCancel, FillOrKill = Value
}

object OrderState extends Enumeration {
  type OrderState = Value
  val Failed, Pending, Accepted, Rejected, CancelPending, Canceled, PartialCanceled, Partial, Executed, ReplacePending, Replaced, Stopped, Suspended, Expired, Queued, Triggered, Activated, PendingRiskReview, ContingentOrder = Value
}

object HistoricalDataGranularity extends Enumeration {
  type HistoricalDataGranularity = Value
  val OneMinute, TwoMinutes, ThreeMinutes, FourMinutes, FiveMinutes, TenMinutes, FifteenMinutes, TwentyMinutes, HalfHour, OneHour, TwoHours, FourHours, OneDay, OneWeek, OneMonth, OneYear = Value
}

object OrderClass extends Enumeration {
  type OrderClass = Value
  val Primary, Limit, StopLoss = Value
}

object StrategyTypes extends Enumeration {
  type StrategyTypes = Value
  val CoveredCall, MarriedPuts, VerticalCallSpread, VerticalPutSpread, CalendarCallSpread, CalendarPutSpread, DiagonalCallSpread, DiagonalPubSpread, Collar, Straddle, Strangle, ButterflyCall, ButterflyPut, IronButterfly, CondorCall, Custom = Value
}