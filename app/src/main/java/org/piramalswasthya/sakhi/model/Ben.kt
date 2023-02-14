package org.piramalswasthya.sakhi.model

import androidx.room.*
import com.squareup.moshi.JsonClass
import org.piramalswasthya.sakhi.database.room.SyncState
import java.text.SimpleDateFormat
import java.util.*

enum class TypeOfList{
    INFANT,
    CHILD,
    ADOLESCENT,
    GENERAL,
    ELIGIBLE_COUPLE,
    ANTENATAL_MOTHER,
    DELIVERY_STAGE,
    POSTNATAL_MOTHER,
    MENOPAUSE,
    TEENAGER,
    OTHER,

}
enum class AgeUnit{
    DAYS,
    MONTHS,
    YEARS
}
enum class Gender{
    MALE,
    FEMALE,
    TRANSGENDER
}
data class BenBasicDomain(
    val benId: Long,
    val hhId: Long,
    val regDate: String,
    val benName: String,
    val benSurname: String? = null,
    val gender: String,
    val age: String,
    val mobileNo: String,
    val fatherName: String? = null,
    val familyHeadName: String,
    val typeOfList: String,
    val rchId: String,
    val hrpStatus: String? = null,
    var syncState: SyncState
)

data class BenRegKid(
    var childName: String? = null,
    var childRegisteredAWC: String? = null,
    var childRegisteredAWCId: Int = 0,
    var childRegisteredSchool: String? = null,
    var childRegisteredSchoolId: Int = 0,
    var typeOfSchool: String? = null,
    var typeOfSchoolId: Int = 0,
    var birthPlace : String? = null,
    var birthPlaceId : String? = null,
    var facilityName : String? = null,
    var facilityid : String? = null,
    var facilityOther : String? = null,
    var placeName : String? = null,
    var conductedDelivery : String? = null,
    var conductedDeliveryId : String? = null,
    var conductedDeliveryOther : String? = null,
    var deliveryType : String? = null,
    var deliveryTypeId : String? = null,
    var complications : String? = null,
    var complicationsId : String? = null,
    var complicationsOther : String? = null,
    var term : String? = null,
    var termid : String? = null,
    var gestationalAge : String? = null,
    var gestationalAgeId : String? = null,
    var corticosteroidGivenMother : String? = null,
    var corticosteroidGivenMotherId : String? = null,
    var criedImmediately : String? = null,
    var criedImmediatelyId : String? = null,
    var birthDefects : String? = null,
    var birthDefectsId : String? = null,
    var birthDefectsOthers : String? = null,
    var heightAtBirth : String? = null,
    var weightAtBirth : String? = null,
    var feedingStarted : String? = null,
    var feedingStartedId : String? = null,
    var birthDosage : String? = null,
    var birthDosageId : String? = null,
    var opvBatchNo : String? = null,
    var opvGivenDueDate : String? = null,
    var opvDate : String? = null,
    var bcdBatchNo : String? = null,
    var bcgGivenDueDate : String? = null,
    var bcgDate : String? = null,
    var hptBatchNo : String? = null,
    var hptGivenDueDate : String? = null,
    var hptDate : String? = null,
    var vitaminKBatchNo : String? = null,
    var vitaminKGivenDueDate : String? = null,
    var vitaminKDate: String? = null,
    var deliveryTypeOther: String? = null,

    var motherBenId: String? = null,
    var childMotherName: String? = null,
    var motherPosition: String? = null,
    var birthBCG: Boolean = false,
    var birthHepB: Boolean = false,
    var birthOPV: Boolean = false,
)

@JsonClass(generateAdapter = true)
data class BenRegKidNetwork(
    var childName: String? = null,
    var childRegisteredAWC: String? = null,
    var childRegisteredAWCId: Int = 0,
    var childRegisteredSchool: String? = null,
    var childRegisteredSchoolId: Int = 0,
    var typeOfSchool: String? = null,
    var typeOfSchoolId: Int = 0,
    var birthPlace: String? = null,
    var birthPlaceId: String? = null,
    var facilityName: String? = null,
    var facilityid: String? = null,
    var facilityOther: String? = null,
    var placeName: String? = null,
    var conductedDelivery: String? = null,
    var conductedDeliveryId: String? = null,
    var conductedDeliveryOther: String? = null,
    var deliveryType: String? = null,
    var deliveryTypeId: String? = null,
    var complications: String? = null,
    var complicationsId: String? = null,
    var complicationsOther: String? = null,
    var term: String? = null,
    var termid: String? = null,
    var gestationalAge: String? = null,
    var gestationalAgeId: String? = null,
    var corticosteroidGivenMother: String? = null,
    var corticosteroidGivenMotherId: String? = null,
    var criedImmediately: String? = null,
    var criedImmediatelyId: String? = null,
    var birthDefects: String? = null,
    var birthDefectsId: String? = null,
    var birthDefectsOthers: String? = null,
    var heightAtBirth: String? = null,
    var weightAtBirth: String? = null,
    var feedingStarted: String? = null,
    var feedingStartedId: String? = null,
    var birthDosage: String? = null,
    var birthDosageId: String? = null,
    var opvBatchNo: String? = null,
    var opvGivenDueDate: String? = null,
    var opvDate: String? = null,
    var bcdBatchNo: String? = null,
    var bcgGivenDueDate: String? = null,
    var bcgDate: String? = null,
    var hptBatchNo: String? = null,
    var hptGivenDueDate: String? = null,
    var hptDate: String? = null,
    var vitaminKBatchNo: String? = null,
    var vitaminKGivenDueDate: String? = null,
    var vitaminKDate: String? = null,
    var deliveryTypeOther: String? = null,

    var motherBenId: String? = null,
    var childMotherName: String? = null,
    var motherPosition: String? = null,
    var birthBCG: Boolean = false,
    var birthHepB: Boolean = false,
    var birthOPV: Boolean = false,
)

data class BenRegGen(

    var maritalStatus: String? = null,
    var maritalStatusId: Int = 0,
    var spouseName: String? = null,
    var ageAtMarriage: Int = 0,
    var dateOfMarriage: Long = 0,
    var marriageDate: String? = null,
    //Menstrual details
    var menstrualStatus: String? = null,
    var menstrualStatusId: Int? = 0,
    var regularityOfMenstrualCycle: String? = null,
    var regularityOfMenstrualCycleId: Int = 0,
    var lengthOfMenstrualCycle: String? = null,
    var lengthOfMenstrualCycleId: Int = 0,
    var menstrualBFD: String? = null,
    var menstrualBFDId: Int = 0,
    var menstrualProblem: String? = null,
    var menstrualProblemId: Int = 0,
    var lastMenstrualPeriod: String? = null,
    var reproductiveStatus: String? = null,
    var reproductiveStatusId: Int = 0,
    var lastDeliveryConducted: String? = null,
    var lastDeliveryConductedId: Int = 0,
    var otherLastDeliveryConducted: String? = null,
    var facilityName: String? = null,
    var whoConductedDelivery: String? = null,
    var whoConductedDeliveryId: Int = 0,
    var noOfDaysForDelivery: Int? = null,
    var otherWhoConductedDelivery: String? = null,
    var deliveryDate: String? = null,
    var expectedDateOfDelivery: String? = null,
    var numPreviousLiveBirth: String? = null,
//    var formStatus: String? = null,
//    var formType: String? = null,
    var ancCount: Int = 0,
    var hrpCount: Int = 0,
    var hrpSuspected: Boolean = false,
    var isDeathStatus: Boolean = false,
)

@Entity(
    tableName = "BENEFICIARY",
    primaryKeys = ["householdId", "beneficiaryId"],
    foreignKeys = [
        ForeignKey(
            entity = HouseholdCache::class,
            parentColumns = arrayOf("householdId"),
            childColumns = arrayOf("householdId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserCache::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("ashaId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class BenRegCache(

    @ColumnInfo(index = true)
    var householdId: Long,

    var beneficiaryId: Long,

    var benRegId: Long = 0,

    @ColumnInfo(index = true)
    var ashaId: Int,

    var isKid: Boolean,

    var isAdult: Boolean,

    var userImage: String? = null,

    @Suppress("ArrayInDataClass")
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var userImageBlob: ByteArray? = null,

    var regDate: Long? = null,

    var firstName: String? = null,

    var lastName: String? = null,

    var gender: Gender? = null,

    var genderId: Int = 0,

    var dob: Long = 0,

    var age: Int = 0,

    var ageUnit: AgeUnit? = null,

    var age_unitId: Int = 0,

    var fatherName: String? = null,

    var motherName: String? = null,

    var familyHeadRelation: String? = null,

    var familyHeadRelationPosition: Int = 0,

    var familyHeadRelationOther: String? = null,

    var mobileNoOfRelation: String? = null,

    var mobileNoOfRelationId: Int = 0,

    var mobileOthers: String? = null,

    var contactNumber: Long? = null,

    var literacy: String? = null,

    var literacyId: Int = 0,

    var community: String? = null,

    var communityId: Int = 0,

    var religion: String? = null,

    var religionId: Int = 0,

    var religionOthers: String? = null,

    var rchId: String? = null,

    var registrationType: TypeOfList? = null,

    var latitude: Double = 0.0,

    var longitude: Double = 0.0,

    ///////////////////////////Bank details Start///////////////////////////
    var hasAadhar: Boolean? = false,

    var hasAadharId: Int = 0,

    var aadharNum: String? = null,

    var aadharNumId: Int = 0,

    var bankAccountId: Int = 0,

    var bankAccount: String? = null,

    var nameOfBank: String? = null,

    var nameOfBranch: String? = null,

    var ifscCode: String? = null,

    ///////////////////////////Bank details End///////////////////////////
    var needOpCare: String? = null,

    var needOpCareId: Int = 0,

    var ncdPriority: Int = 0,

    var cbacAvailable: Boolean = false,

    var guidelineId: String? = null,

    var isHrpStatus: Boolean = false,

    var immunizationStatus: Boolean = false,

    var hrpIdentificationDate: String? = null,

    var hrpLastVisitDate: String? = null,

    var nishchayPregnancyStatus: String? = null,

    var nishchayPregnancyStatusPosition: Int = 0,

    var nishchayDeliveryStatus: String? = null,

    var nishchayDeliveryStatusPosition: Int = 0,

    var nayiPahalDeliveryStatus: String? = null,

    var nayiPahalDeliveryStatusPosition: Int = 0,

    var suspectedNcd: String? = null,

    var suspectedNcdDiseases: String? = null,

    var suspectedTb: String? = null,

    var confirmed_Ncd: String? = null,

    var confirmedHrp: String? = null,

    var confirmedTb: String? = null,

    var confirmedNcdDiseases: String? = null,

    var diagnosisStatus: String? = null,




    /*
    5 Skipped:
        Aadhar, lastHrpVisitDate, marriageDate ( 2 copies)
        vanId and serviceMap ID, ( Can get from Foreign key)
     */


    @Embedded(prefix = "kid_")
    var kidDetails: BenRegKid? = null,

    @Embedded(prefix = "gen_")
    var genDetails: BenRegGen? = null,

    @Embedded(prefix = "loc_")
    var locationRecord: LocationRecord? = null,

    var processed: String? = null,

    var serverUpdatedStatus: Int = 0,

    var createdBy: String? = null,

    var createdDate: Long? = null,

    var updatedBy: String? = null,

    var updatedDate: Long? = null,

    var syncState: SyncState,

    var isDraft: Boolean,

    ){

    companion object{
         val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    }
    fun asBasicDomainModel() : BenBasicDomain{
        return BenBasicDomain(
            benId = beneficiaryId,
            hhId = householdId,
            regDate = dateFormat.format(Date(regDate!!)),
            benName = firstName ?: "Not Available",
            benSurname = lastName ?: "Not Available",
            gender = gender?.name ?: "Not Available",
            age = if (age == 0) "Not Available" else "$age $ageUnit",
            mobileNo = contactNumber?.toString() ?: "Not Available",
            fatherName = fatherName,
            familyHeadName = "Not implemented at the moment!",
            typeOfList = registrationType?.name ?: "Not Available",
            rchId = rchId ?: "Not Available",
            hrpStatus = confirmedHrp ?: "Not Available",
            syncState = syncState
        )
    }


    fun asNetworkPostModel(user: UserCache): BenPost {
        return BenPost(
            houseoldId = householdId.toString(),
            benficieryid = beneficiaryId,
            ashaid = ashaId,
            registrationDate = getDateTimeStringFromLong(regDate!!),
            age = age,
            age_unit = when (ageUnit) {
                AgeUnit.YEARS -> "Year(s)"
                AgeUnit.MONTHS -> "Month(s)"
                AgeUnit.DAYS -> "Day(s)"
                else -> null
            },
            age_unitId = ageUnit!!.ordinal,
            marriageDate = genDetails?.marriageDate,
            mobilenoofRelation = mobileNoOfRelation,
            mobilenoofRelationId = mobileNoOfRelationId,
            mobileOthers = mobileOthers,
            literacy = literacy,
            literacyId = literacyId,
            religionOthers = religionOthers,
            rchid = rchId,
            registrationType = when (registrationType) {
                TypeOfList.INFANT -> "Infant"
                TypeOfList.CHILD -> "Child"
                TypeOfList.ADOLESCENT -> "Adolescent"
                TypeOfList.GENERAL -> "General"
                TypeOfList.ELIGIBLE_COUPLE -> "Eligible"
                TypeOfList.ANTENATAL_MOTHER -> "Antenatal"
                TypeOfList.DELIVERY_STAGE -> "Delivery"
                TypeOfList.POSTNATAL_MOTHER -> "Postnatal"
                TypeOfList.MENOPAUSE -> "Menopause"
                TypeOfList.TEENAGER -> "Teenager"
                else -> "Other"
            },
            latitude = latitude,
            longitude = longitude,
            aadhaNo = aadharNum,
            aadha_no = if (hasAadhar == true) "Yes" else "No",
            aadha_noId = hasAadharId,
            need_opcare = needOpCare,
            need_opcareId = needOpCareId,
            menstrualStatusId = genDetails?.menstrualStatusId ?: 0,
            regularityofMenstrualCycleId = genDetails?.regularityOfMenstrualCycleId ?: 0,
            lengthofMenstrualCycleId = genDetails?.lengthOfMenstrualCycleId ?: 0,
            menstrualBFDId = genDetails?.menstrualBFDId ?: 0,
            menstrualProblemId = genDetails?.menstrualProblemId ?: 0,
            lastMenstrualPeriod = genDetails?.lastMenstrualPeriod,
            reproductiveStatus = genDetails?.reproductiveStatus,
            reproductiveStatusId = genDetails?.reproductiveStatusId ?: 0,
            noOfDaysForDelivery = 0,
            formStatus = null,
            formType = null,
            childRegisteredAWCID = kidDetails?.childRegisteredAWCId ?: 0,
            childRegisteredSchoolID = kidDetails?.childRegisteredSchoolId ?: 0,
            typeofSchoolID = kidDetails?.typeOfSchoolId ?: 0,
            childRegisteredAWC = kidDetails?.childRegisteredAWC,
            childRegisteredSchool = kidDetails?.childRegisteredSchool,
            typeofSchool = kidDetails?.typeOfSchool,
            previousLiveBirth = genDetails?.numPreviousLiveBirth,
            lastDeliveryConductedID = genDetails?.lastDeliveryConductedId ?: 0,
            whoConductedDeliveryID = genDetails?.whoConductedDeliveryId ?: 0,
            familyHeadRelation = familyHeadRelation,
            familyHeadRelationPosition = familyHeadRelationPosition,
            whoConductedDelivery = genDetails?.whoConductedDelivery,
            lastDeliveryConducted = genDetails?.lastDeliveryConducted,
            facilitySelection = genDetails?.facilityName,
            serverUpdatedStatus = serverUpdatedStatus,
            createdBy = createdBy,
            createdDate = getDateTimeStringFromLong(createdDate!!),
            ncd_priority = ncdPriority,
            guidelineId = guidelineId,
            villagename = locationRecord?.village,
            vanID = user.vanId,
            countyid = locationRecord?.countryId!!,
            providerServiceMapID = user.serviceMapId,
            processed = processed,
            currSubDistrictId = locationRecord?.villageId!!,
            expectedDateOfDelivery = genDetails?.expectedDateOfDelivery,
            isHrpStatus = isHrpStatus,
            menstrualStatus = genDetails?.menstrualStatus,
            ageAtMarriage = genDetails?.ageAtMarriage ?: 0,
            dateMarriage = genDetails?.dateOfMarriage?.let { getDateTimeStringFromLong(it) },
            deliveryDate = genDetails?.deliveryDate,
            suspected_hrp = genDetails?.hrpSuspected.toString(),
            suspected_ncd = suspectedNcd,
            suspected_tb = suspectedTb,
            suspected_ncd_diseases = suspectedNcdDiseases,
            confirmed_ncd = confirmed_Ncd,
            confirmed_hrp = confirmedHrp,
            confirmed_tb = confirmedTb,
            confirmed_ncd_diseases = confirmedNcdDiseases,
            diagnosis_status = diagnosisStatus,
            nishchayPregnancyStatus = nishchayPregnancyStatus,
            nishchayPregnancyStatusPosition = nishchayPregnancyStatusPosition,
            nishchayDeliveryStatus = nishchayDeliveryStatus,
            nishchayDeliveryStatusPosition = nishchayDeliveryStatusPosition,
            nayiPahalDeliveryStatus = nayiPahalDeliveryStatus,
            nayiPahalDeliveryStatusPosition = nayiPahalDeliveryStatusPosition,
            isImmunizationStatus = immunizationStatus,
        )
    }

    fun asKidNetworkModel(): BenRegKidNetwork {
        return BenRegKidNetwork(

        )
    }
}

fun getDateTimeStringFromLong(dateLong: Long?): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
    dateLong?.let {
        val dateString = dateFormat.format(dateLong)
        val timeString = timeFormat.format(dateLong)
        return "${dateString}T${timeString}.000Z"
    } ?: run {
        return null
    }

}
/*

data class BenRegNetwork(
    @Json(name = "houseoldId")
    var houseoldId: String,

    @PrimaryKey
    @Json(name = "benficieryid")
    var benficieryid: Long,

    //Benficiery Registration fields
    @Json(name = "user_image")
    var user_image: String? = null,

    @Json(name = "ashaid")
    var ashaid: Int,

    @Suppress("ArrayInDataClass")
    var user_image1: String? = null,

    @Json(name = "registrationDate")
    var registrationDate: String? = null,

    @Json(name = "firstName")
    var firstName: String? = null,

    @Json(name = "lastName")
    var lastName: String? = null,

    @Json(name = "gender")
    var gender: String? = null,

    @Json(name = "genderId")
    var genderId: Int = 0,

    @Json(name = "dob")
    var dob: String? = null,

    @Json(name = "age")
    var age: Int = 0,

    @Json(name = "age_unit")
    var age_unit: String? = null,

    @Json(name = "age_unitId")
    var age_unitId: Int = 0,

    @Json(name = "maritalstatus")
    var maritalstatus: String? = null,

    @Json(name = "maritalstatusId")
    var maritalstatusId: Int = 0,

    @Json(name = "spousename")
    var spousename: String? = null,

    @Json(name = "ageAtMarriage")
    var ageAtMarriage: Int = 0,

    @Json(name = "dateMarriage")
    var dateMarriage: String? = null,

    @Json(name = "marriageDate")
    var marriageDate: String? = null,

    @Json(name = "fatherName")
    var fatherName: String? = null,

    @Json(name = "motherName")
    var motherName: String? = null,

    @Json(name = "contact_number")
    var contact_number: String? = null,

    @Json(name = "mobilenoofRelation")
    var mobilenoofRelation: String? = null,

    @Json(name = "mobilenoofRelationId")
    var mobilenoofRelationId: Int = 0,

    @Json(name = "mobileOthers")
    var mobileOthers: String? = null,

    @Json(name = "literacy")
    var literacy: String? = null,

    @Json(name = "literacyId")
    var literacyId: Int = 0,

    @Json(name = "community")
    var community: String? = null,

    @Json(name = "communityId")
    var communityId: Int = 0,

    @Json(name = "religion")
    var religion: String? = null,

    @Json(name = "religionID")
    var religionID: Int = 0,

    @Json(name = "religionOthers")
    var religionOthers: String? = null,

    @Json(name = "rchid")
    var rchid: String? = null,

    @Json(name = "registrationType")
    var registrationType: String? = null,

    @Json(name = "latitude")
    var latitude: Double = 0.0,

    @Json(name = "longitude")
    var longitude: Double = 0.0,

    //Bank details
    @Json(name = "aadha_no")
    var aadha_no: String? = null,

    @Json(name = "aadha_noId")
    var aadha_noId: Int = 0,

    @Json(name = "aadhaNo")
    var aadhaNo: String? = null,

    @Json(name = "bank_account")
    var bank_account: String? = null,

    @Json(name = "bank_accountId")
    var bank_accountId: Int = 0,

    @Json(name = "bankAccount")
    var bankAccount: String? = null,

    @Json(name = "nameOfBank")
    var nameOfBank: String? = null,

    @Json(name = "nameOfBranch")
    var nameOfBranch: String? = null,

    @Json(name = "ifscCode")
    var ifscCode: String? = null,

    @Json(name = "need_opcare")
    var need_opcare: String? = null,

    @Json(name = "need_opcareId")
    var need_opcareId: Int = 0,

    //Menstral details
    @Json(name = "menstrualStatus")
    var menstrualStatus: String? = null,

    @Json(name = "menstrualStatusId")
    var menstrualStatusId: Int = 0,

    @Json(name = "regularityofMenstrualCycle")
    var regularityofMenstrualCycle: String? = null,

    @Json(name = "regularityofMenstrualCycleId")
    var regularityofMenstrualCycleId: Int = 0,

    @Json(name = "lengthofMenstrualCycle")
    var lengthofMenstrualCycle: String? = null,

    @Json(name = "lengthofMenstrualCycleId")
    var lengthofMenstrualCycleId: Int = 0,

    @Json(name = "menstrualBFD")
    var menstrualBFD: String? = null,

    @Json(name = "menstrualBFDId")
    var menstrualBFDId: Int = 0,

    @Json(name = "menstrualProblem")
    var menstrualProblem: String? = null,

    @Json(name = "menstrualProblemId")
    var menstrualProblemId: Int = 0,

    @Json(name = "lastMenstrualPeriod")
    var lastMenstrualPeriod: String? = null,

    @Json(name = "reproductiveStatus")
    var reproductiveStatus: String? = null,

    @Json(name = "reproductiveStatusId")
    var reproductiveStatusId: Int = 0,

    @Json(name = "formStatus")
    var formStatus: String? = null,

    @Json(name = "formType")
    var formType: String? = null,

    @Json(name = "ANCCount")
    var aNCCount: Int = 0,

    @Json(name = "HRPCount")
    var hRPCount: Int = 0,

    @Json(name = "hrp_suspected")
    var hrp_suspected: Boolean? = null,

    @Json(name = "death_status")
    var isDeath_status: Boolean = false,

    @Json(name = "childRegisteredAWC")
    var childRegisteredAWC: String? = null,

    @Json(name = "childRegisteredAWCID")
    var childRegisteredAWCID: Int = 0,

    @Json(name = "childRegisteredSchool")
    var childRegisteredSchool: String? = null,

    @Json(name = "childRegisteredSchoolID")
    var childRegisteredSchoolID: Int = 0,

    @Json(name = "TypeofSchool")
    var typeofSchool: String? = null,

    @Json(name = "TypeofSchoolID")
    var typeofSchoolID: Int = 0,

    @Json(name = "expectedDateOfDelivery")
    var expectedDateOfDelivery: String? = null,

    @Json(name = "PreviousLiveBirth")
    var previousLiveBirth: String? = null,

    //these are new fields of new borr registartion
    @Json(name = "LastDeliveryConducted")
    var lastDeliveryConducted: String? = null,

    @Json(name = "LastDeliveryConductedID")
    var lastDeliveryConductedID: Int = 0,

    @Json(name = "facilitySelection")
    var facilitySelection: String? = null,

    //    @Json(name = "FacilitySectionID")
    //    private int facilitySectionID;

    @Json(name = "WhoConductedDelivery")
    var whoConductedDelivery: String? = null,

    @Json(name = "WhoConductedDeliveryID")
    var whoConductedDeliveryID: Int = 0,

    //these are new fields of registration for asha login
    @Json(name = "FamilyHeadRelation")
    var familyHeadRelation: String? = null,

    @Json(name = "FamilyHeadRelationPosition")
    var familyHeadRelationPosition: Int = 0,

    */
/*@Json(name = "PreviousLiveBirthID")
      private int PreviousLiveBirthID;*//*

    @Json(name = "ServerUpdatedStatus")
    var serverUpdatedStatus: Int = 0,

    @Json(name = "createdBy")
    var createdBy: String? = null,

    @Json(name = "createdDate")
    var createdDate: String? = null,

    @Json(name = "updatedBy")
    var updatedBy: String? = null,

    @Json(name = "updatedDate")
    var updatedDate: String? = null,

    @Json(name = "ncd_priority")
    var ncd_priority: Int = 0,

    @Json(name = "cbac_available")
    var cbac_available: Boolean = false,

    @Json(name = "guidelineId")
    var guidelineId: String? = null,

    @Json(name = "villagename")
    var villagename: String? = null,

    @Json(name = "deliveryDate")
    var deliveryDate: String? = null,

    @Json(name = "BenRegId")
    var benRegId: Int = 0,

    @Json(name = "ProviderServiceMapID")
    var providerServiceMapID: Int = 0,

    @Json(name = "VanID")
    var vanID: Int = 0,

    @Json(name = "Processed")
    var processed: String? = null,

    @Json(name = "Countyid")
    var countyid: Int = 0,

    @Json(name = "stateid")
    var stateid: Int = 0,

    @Json(name = "districtid")
    var districtid: Int = 0,

    @Json(name = "districtname")
    var districtname: String? = null,

    @Json(name = "currSubDistrictId")
    var currSubDistrictId: Int = 0,

    @Json(name = "villageid")
    var villageid: Int = 0,

    @Json(name = "childname")
    var childname: String? = null,

    @Json(name = "childBenId")
    var childBenId: Int = 0,

    @Json(name = "childpos")
    var childpos: Int = 0,

    @Json(name = "motherBenId")
    var motherBenId: Int = 0,

    @Json(name = "hrpStatus")
    var isHrpStatus: Boolean = false,

    //    @Json(name = "relatedBeneficiaryIds")
    //    List<RelatedBenIds> relatedBeneficiaryIds;

    @Json(name = "hrp_identification_date")
    var hrp_identification_date: String? = null,

    @Json(name = "hrp_last_vist_date")
    var hrp_last_vist_date: String? = null,

    @Json(name = "lastHrpVisitDate")
    var lastHrpVisitDate: String? = null,

    @Json(name = "nishchayPregnancyStatus")
    var nishchayPregnancyStatus: String? = null,

    @Json(name = "nishchayPregnancyStatusPosition")
    var nishchayPregnancyStatusPosition: Int = 0,

    @Json(name = "nishchayDeliveryStatus")
    var nishchayDeliveryStatus: String? = null,

    @Json(name = "nishchayDeliveryStatusPosition")
    var nishchayDeliveryStatusPosition: Int = 0,

    @Json(name = "nayiPahalDeliveryStatus")
    var nayiPahalDeliveryStatus: String? = null,

    @Json(name = "nayiPahalDeliveryStatusPosition")
    var nayiPahalDeliveryStatusPosition: Int = 0,

    @Json(name = "suspected_hrp")
    var suspected_hrp: String? = null,

    @Json(name = "suspected_ncd")
    var suspected_ncd: String? = null,

    @Json(name = "suspected_tb")
    var suspected_tb: String? = null,

    @Json(name = "suspected_ncd_diseases")
    var suspected_ncd_diseases: String? = null,

    @Json(name = "confirmed_ncd")
    var confirmed_ncd: String? = null,

    @Json(name = "confirmed_hrp")
    var confirmed_hrp: String? = null,

    @Json(name = "confirmed_tb")
    var confirmed_tb: String? = null,

    @Json(name = "confirmed_ncd_diseases")
    var confirmed_ncd_diseases: String? = null,

    @Json(name = "diagnosis_status")
    var diagnosis_status: String? = null,

    @Json(name = "facilityOther")
    var facilityOther: String? = null,

    @Json(name = "noOfDaysForDelivery")
    var noOfDaysForDelivery: Int? = null,

    //    public String getFacility_other() {
    @Json(name = "FamilyHeadRelationOther")
    var familyHeadRelationOther: String? = null,

    @Json(name = "immunizationStatus")
    var isImmunizationStatus: Boolean = false
)
fun asCacheModel(benRegNetwork: BenRegNetwork, newBornRegNetwork: NewBornRegNetwork?): BenRegCache {

    benRegNetwork.apply {
       val ben = BenRegCache(
           householdId = houseoldId.toLong(),
           beneficiaryId = benficieryid,
           ashaId = ashaid,
           age = age,
           ageUnit = when (age_unit) {
               "Year(s)" -> AgeUnit.YEARS
               "Month(s)" -> AgeUnit.MONTHS
               "Day(s)" -> AgeUnit.DAYS
               else -> AgeUnit.YEARS
           },
           isKid = !(age_unit == "Year(s)" && age > 14),
           isAdult = (age_unit == "Year(s)" && age > 14),
           userImage = user_image,
           userImageBlob = user_image1!!.toByteArray(),
           regDate = getLongFromDate(registrationDate),
           firstName = firstName,
           lastName = lastName,
           gender = when (gender) {
               "Male" -> Gender.MALE
               "Female" -> Gender.FEMALE
               "Transgender" -> Gender.TRANSGENDER
               else -> Gender.MALE
           },
           genderId = genderId,
           dob = getLongFromDate(dob),
           age_unitId = age_unitId,
           fatherName = fatherName,
           motherName = motherName,
           familyHeadRelation = familyHeadRelation,
           familyHeadRelationPosition = familyHeadRelationPosition,
           familyHeadRelationOther = familyHeadRelationOther,
           mobileNoOfRelation = mobilenoofRelation,
           mobileNoOfRelationId = mobilenoofRelationId,
           mobileOthers = mobileOthers,
           contactNumber = contact_number?.toLong() ?:0L,
           literacy = literacy,
           literacyId = literacyId,
           community = community,
           communityId = communityId,
           religion = religion,
           religionId = religionID,
           religionOthers = religionOthers,
           rchId = rchid,
           registrationType = when(registrationType) {
               "Infant" -> TypeOfList.INFANT
               "Child" -> TypeOfList.CHILD
               "Adolescent" -> TypeOfList.ADOLESCENT
               "General" -> TypeOfList.GENERAL
               "Eligible Couple" -> TypeOfList.ELIGIBLE_COUPLE
               "Antenatal Mother" -> TypeOfList.ANTENATAL_MOTHER
               "Delivery Stage" -> TypeOfList.DELIVERY_STAGE
               "Postnatal Mother" -> TypeOfList.POSTNATAL_MOTHER
               "Menopause" -> TypeOfList.MENOPAUSE
               "Teenager" -> TypeOfList.TEENAGER
               else -> TypeOfList.OTHER
           },
           latitude = latitude,
           longitude = longitude,
           aadharNum = aadhaNo,
           aadharNumId = aadha_noId,
           hasAadhar = (aadhaNo != null),
           hasAadharId = aadha_noId,
           bankAccountId = bank_accountId,
           bankAccount = bankAccount,
           nameOfBank = nameOfBank,
           nameOfBranch = nameOfBranch,
           ifscCode = ifscCode,
           needOpCare = need_opcare,
           needOpCareId = need_opcareId,
           ncdPriority = ncd_priority,
           cbacAvailable = cbac_available,
           guidelineId = guidelineId,
           isHrpStatus = isHrpStatus,
           hrpIdentificationDate = hrp_identification_date,
           hrpLastVisitDate = hrp_last_vist_date,
           nishchayPregnancyStatus = nishchayPregnancyStatus,
           nishchayPregnancyStatusPosition = nishchayPregnancyStatusPosition,
           nishchayDeliveryStatus = nishchayDeliveryStatus,
           nishchayDeliveryStatusPosition = nishchayDeliveryStatusPosition,
           nayiPahalDeliveryStatus = nayiPahalDeliveryStatus,
           nayiPahalDeliveryStatusPosition = nayiPahalDeliveryStatusPosition,
           suspectedNcd = suspected_ncd,
           suspectedNcdDiseases = suspected_ncd_diseases,
           suspectedTb = suspected_tb,
           confirmed_Ncd = confirmed_ncd,
           confirmedHrp = confirmed_hrp,
           confirmedTb = confirmed_tb,
           confirmedNcdDiseases = confirmed_ncd_diseases,
           diagnosisStatus = diagnosis_status,
           countryId = countyid,
           stateId = stateid,
           districtId = districtid,
           districtName = districtname,
           currSubDistrictId = currSubDistrictId,
           villageId = villageid,
           villageName = villagename,
           processed = processed,
           serverUpdatedStatus = serverUpdatedStatus,
           createdBy = createdBy,
           createdDate = getLongFromDate(updatedDate),
           kidDetails = BenRegKid(
               childRegisteredAWC = childRegisteredAWC,
               childRegisteredAWCId = childRegisteredAWCID,
               childRegisteredSchool = childRegisteredSchool,
               childRegisteredSchoolId = childRegisteredSchoolID,
               typeOfSchool = typeofSchool,
               typeOfSchoolId = typeofSchoolID
               ),
           genDetails = BenRegGen(
               maritalStatus = maritalstatus,
               maritalStatusId = maritalstatusId,
               spouseName = spousename,
               ageAtMarriage = ageAtMarriage,
               dateOfMarriage = getLongFromDate(dateMarriage),
               marriageDate = marriageDate,
               menstrualStatus = menstrualStatus,
               menstrualStatusId = menstrualStatusId,
               regularityOfMenstrualCycle = regularityofMenstrualCycle,
               regularityOfMenstrualCycleId = regularityofMenstrualCycleId,
               lengthOfMenstrualCycle = lengthofMenstrualCycle,
               lengthOfMenstrualCycleId = lengthofMenstrualCycleId,
               menstrualBFD = menstrualBFD,
               menstrualBFDId = menstrualBFDId,
               menstrualProblem = menstrualProblem,
               menstrualProblemId = menstrualProblemId,
               lastMenstrualPeriod = lastMenstrualPeriod,
               reproductiveStatus = reproductiveStatus,
               reproductiveStatusId = reproductiveStatusId,
               lastDeliveryConducted = lastDeliveryConducted,
               lastDeliveryConductedId = lastDeliveryConductedID,
               facilityName = facilitySelection,
               whoConductedDelivery = whoConductedDelivery,
               whoConductedDeliveryId = whoConductedDeliveryID,
               deliveryDate = deliveryDate,
               expectedDateOfDelivery = expectedDateOfDelivery,
               noOfDaysForDelivery = noOfDaysForDelivery,
               ),
           syncState = SyncState.UNSYNCED,
           isDraft = false
       )
        newBornRegNetwork?.let {
            ben.kidDetails?.childName = it.childName
            ben.kidDetails?.birthPlace = it.birthPlace
            ben.kidDetails?.birthPlaceId = it.birthPlaceid.toString()
            ben.kidDetails?.facilityName = it.facilityName
            ben.kidDetails?.facilityid = it.facilityid.toString()
            ben.kidDetails?.facilityOther = it.facilityOther
            ben.kidDetails?.placeName = it.placeName
            ben.kidDetails?.conductedDelivery = it.conductedDelivery
            ben.kidDetails?.conductedDeliveryId = it.conductedDeliveryid.toString()
            ben.kidDetails?.conductedDeliveryOther = it.conductedDeliveryOther
            ben.kidDetails?.deliveryType = it.deliveryType
            ben.kidDetails?.deliveryTypeId = it.deliveryTypeid.toString()
            ben.kidDetails?.complications = it.complecations
            ben.kidDetails?.complicationsId = it.complecationsid.toString()
            ben.kidDetails?.complicationsOther = it.complicationsOther
            ben.kidDetails?.term = it.term
            ben.kidDetails?.termid = it.termid.toString()
            ben.kidDetails?.gestationalAge = it.gestationalAge
            ben.kidDetails?.gestationalAgeId = it.gestationalAgeid.toString()
            ben.kidDetails?.corticosteroidGivenMother = it.corticosteroidGivenMother
            ben.kidDetails?.corticosteroidGivenMotherId = it.corticosteroidGivenMotherid.toString()
            ben.kidDetails?.criedImmediately = it.criedImmediately
            ben.kidDetails?.criedImmediatelyId = it.criedImmediatelyid.toString()
            ben.kidDetails?.birthDefects = it.birthDefects
            ben.kidDetails?.birthDefectsId = it.birthDefectsid.toString()
            ben.kidDetails?.heightAtBirth = it.heightAtBirth.toString()
            ben.kidDetails?.weightAtBirth = it.weightAtBirth.toString()
            ben.kidDetails?.feedingStarted = it.feedingStarted
            ben.kidDetails?.feedingStartedId = it.feedingStartedid.toString()
            ben.kidDetails?.birthDosage = it.birthDosage
            ben.kidDetails?.birthDosageId = it.birthDosageid.toString()
            ben.kidDetails?.opvBatchNo = it.opvBatchNo
            ben.kidDetails?.opvGivenDueDate = it.opvGivenDueDate
            ben.kidDetails?.opvDate = it.opvDate
            ben.kidDetails?.bcdBatchNo = it.bcdBatchNo
            ben.kidDetails?.bcgGivenDueDate = it.bcgGivenDueDate
            ben.kidDetails?.bcgDate = it.bcgDate
            ben.kidDetails?.hptBatchNo = it.hptdBatchNo
            ben.kidDetails?.hptGivenDueDate = it.hptGivenDueDate
            ben.kidDetails?.hptDate = it.hptDate
            ben.kidDetails?.vitaminKBatchNo = it.vitaminkBatchNo
            ben.kidDetails?.vitaminKGivenDueDate = it.vitaminkGivenDueDate
            ben.kidDetails?.vitaminKDate = it.vitaminkDate
            ben.kidDetails?.deliveryTypeOther = it.deliveryTypeOther
            ben.kidDetails?.motherBenId = it.motherBenId.toString()
            ben.kidDetails?.childMotherName = it.motherName
            ben.kidDetails?.motherPosition = it.motherposition.toString()
            ben.kidDetails?.birthBCG = it.birthBCG
            ben.kidDetails?.birthHepB = it.birthHepB
            ben.kidDetails?.birthOPV = it.birthOPV
        }
        return ben
   }

}

*/

