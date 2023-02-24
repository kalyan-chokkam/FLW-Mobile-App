package org.piramalswasthya.sakhi.model

data class HBYCCache (
    var month: String? = null,
    var subcenterName: String? = null,
    var year: String? = null,
    var primaryHealthCenterName: String? = null,
    var villagePopulation: String? = null,
    var infantPopulation: String? = null,
    var visitdate: Long = 0,
    var hbycAgeCategory: String? = null,
    var orsPacketDelivered: Boolean? = null,
    var ironFolicAcidGiven: Boolean? = null,
    var isVaccinatedByAge: Boolean? = null,
    var wasIll: Boolean? = null,
    var referred: Boolean? = null,
    var supplementsGiven: Boolean? = null,
    var byHeightLength: Boolean? = null,
    var childrenWeighingLessReferred: Boolean? = null,
    var weightAccordingToAge: Boolean? = null,
    var delayInDevelopment: Boolean? = null,
    var referredToHealthInstitite: Boolean? = null,
    var vitaminASupplementsGiven: Boolean? = null,
    var deathAge: String? = null,
    var deathCause: String? = null,
    var qmOrAnmInformed: Boolean? = null,
    var deathPlace: String? = null,
    var superVisorOn: Boolean? = null,
    var orsShortage: Boolean? = null,
    var ifaDecreased: Boolean? = null
)