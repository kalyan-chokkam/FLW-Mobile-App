package org.piramalswasthya.sakhi.repositories

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.map
import org.piramalswasthya.sakhi.database.room.dao.BenDao
import org.piramalswasthya.sakhi.database.room.dao.HouseholdDao
import org.piramalswasthya.sakhi.database.shared_preferences.PreferenceDao
import javax.inject.Inject

@ActivityRetainedScoped
class RecordsRepo @Inject constructor(
    householdDao: HouseholdDao,
    benDao: BenDao,
    preferenceDao: PreferenceDao
) {
    private val selectedVillage = preferenceDao.getLocationRecord()!!.villageId

    val hhList = householdDao.getAllHouseholds(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModel() } }
    val hhCount = hhList.map { it.size }

    val benList = benDao.getAllBen(selectedVillage).map { list -> list.map { it.asBasicDomainModel() } }
    val benListCount = benList.map { it.size }

    val eligibleCoupleList = benDao.getAllEligibleCoupleList(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModel() } }
    val eligibleCoupleListCount = eligibleCoupleList.map { it.size }

    val pregnantList = benDao.getAllPregnancyWomenList(selectedVillage)
        .map { list -> list.map { it.asBenBasicDomainModelForPmsmaForm() } }
    val pregnantListCount = pregnantList.map { it.size }

    val deliveryList = benDao.getAllDeliveryStageWomenList(selectedVillage)
        .map { list -> list.map { it.asBenBasicDomainModelForPmsmaForm() } }
    val deliveryListCount = deliveryList.map { it.size }

    val ncdList = benDao.getAllNCDList(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModel() } }
    val ncdListCount = ncdList.map { it.size }

    val ncdEligibleList = benDao.getAllNCDEligibleList(selectedVillage)
        .map { list -> list.map { it.asBenBasicDomainModelForCbacForm() } }
    val ncdEligibleListCount = ncdEligibleList.map { it.size }

    val ncdPriorityList = benDao.getAllNCDPriorityList(selectedVillage)
        .map { list -> list.map { it.asBenBasicDomainModelForCbacForm() } }
    val ncdPriorityListCount = ncdPriorityList.map { it.size }

    val ncdNonEligibleList = benDao.getAllNCDNonEligibleList(selectedVillage)
        .map { list -> list.map { it.asBenBasicDomainModelForCbacForm() } }
    val ncdNonEligibleListCount = ncdNonEligibleList.map { it.size }

    val menopauseList = benDao.getAllMenopauseStageList(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModel() } }
    val menopauseListCount = menopauseList.map { it.size }

    val reproductiveAgeList = benDao.getAllReproductiveAgeList(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModelForFpotForm() } }
    val reproductiveAgeListCount = reproductiveAgeList.map { it.size }

    val infantList = benDao.getAllInfantList(selectedVillage)
        .map { list -> list.map { it.asBenBasicDomainModelForHbncForm() } }
    val infantListCount = infantList.map { it.size }

    val childList = benDao.getAllChildList(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModel() } }
    val childListCount = childList.map { it.size }

    val adolescentList =
        benDao.getAllAdolescentList(selectedVillage)
            .map { list -> list.map { it.asBasicDomainModel() } }
    val adolescentListCount = adolescentList.map { it.size }

    val immunizationList = benDao.getAllImmunizationDueList(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModel() } }
    val immunizationListCount = menopauseList.map { it.size }

    val hrpList =
        benDao.getAllHrpCasesList(selectedVillage).map { list -> list.map { it.asBasicDomainModel() } }
    val hrpListCount = menopauseList.map { it.size }

    val pncMotherList = benDao.getAllPNCMotherList(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModelForPmjayForm() } }
    val pncMotherListCount = pncMotherList.map { it.size }

    val cdrList = benDao.getAllCDRList(selectedVillage)
        .map { list -> list.map { it.asBenBasicDomainModelForCdrForm() } }
//    val cdrListCount = cdrList.map { it.size }

    val mdsrList = benDao.getAllMDSRList(selectedVillage)
        .map { list -> list.map { it.asBenBasicDomainModelForMdsrForm() } }

    val childrenImmunizationList = benDao.getAllChildrenImmunizationList(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModel() } }
    val childrenImmunizationListCount = childrenImmunizationList.map { it.size }

    val motherImmunizationList = benDao.getAllMotherImmunizationList(selectedVillage)
        .map { list -> list.map { it.asBasicDomainModel() } }
    val motherImmunizationListCount = motherImmunizationList.map { it.size }
}