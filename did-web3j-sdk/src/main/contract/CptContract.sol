pragma solidity ^0.5.0;

import "./LibBytesMap.sol";
import "./LibBytesMulMap.sol";
import "./UtilsContract.sol";
import "./Ownable.sol";
pragma experimental ABIEncoderV2;

/**
 * @title CptContract
 */
contract CptContract is Ownable{

    using LibBytesMap for LibBytesMap.Map;
    LibBytesMap.Map private cptMap;
    LibBytesMap.Map private credIdInfoMap;

    using LibBytesMulMap for LibBytesMulMap.Map;
    LibBytesMulMap.Map private didCptMap;
    LibBytesMulMap.Map private didCreIdListMap;

    uint256 cptNum = 0;

    //Error code
    string constant private SUCCESS = "0000";
    string constant private CPT_NOT_EXIST = "2001";
    string constant private CPT_ALREADY_EXIST = "2002";
    string constant private CREDLIST_NOT_EXIST = "2003";
    string constant private CREDID_NOT_EXIST = "2004";
    string constant private CPTLIST_NOT_EXIST = "2005";
    string constant private DID_NOT_CORRESPOND_TO_CPTID = "2006";
    string constant private VOUCHER_HAS_BEEN_REVOKED = "2007";
    string constant private CREDINFO_NOT_EXIST = "2008";

    event registerCptRetLog(
        string _msgcode,
        string _msg
    );

    event updateCptRetLog(
        string _msgcode,
        string _msg
    );

    event revokeCredentialRetLog(
        string _msgcode,
        string _msg
    );

    /**
     * Rigister cpt
     *
     * @param _did the did
     * @param _cptId the cptId
     * @param _cpt the cpt info
     * @return _msgcode the code for result
     * @return _msg the msg for result
     */
    function registerCpt(
        string memory _did,
        string memory _cptId,
        string memory _cpt
    )
    public
    onlyOwner
    returns (
        string memory _msgcode,
        string memory _msg
    )
    {
        bytes memory _didBytes = bytes(_did);
        bytes memory _cptIdBytes = bytes(_cptId);
        bytes memory _cptBytes = bytes(_cpt);
        bytes[] memory values = new bytes[](1);
        bool bools = isCptListExist(_did);
        bool boolss = isCptExist(_cptId);
        if (bools && boolss) {
            emit registerCptRetLog(CPT_ALREADY_EXIST, "cpt already exist");
            return (CPT_ALREADY_EXIST, "cpt already exist");
        }
        if (bools){
            bytes[] memory oldDidCptList = didCptMap.getValue(_didBytes);
            bytes[] memory newDidCptList = new bytes[](oldDidCptList.length+1);
            for (uint256 x = 0; x < oldDidCptList.length; x++){
                newDidCptList[x] = oldDidCptList[x];
            }
            newDidCptList[newDidCptList.length-1] = _cptIdBytes;
            didCptMap.put(_didBytes, newDidCptList);
            cptMap.put(_cptIdBytes, _cptBytes);
            cptNum = cptNum + 1;
        }else{
            values[values.length-1] = _cptIdBytes;
            didCptMap.put(_didBytes, values);
            cptMap.put(_cptIdBytes, _cptBytes);
            cptNum = cptNum + 1;
        }
        emit registerCptRetLog(SUCCESS, "success");
        return (SUCCESS, "success");
    }


    /**
     * Get cpt by cptid
     *
     * @param _cptId the cpt Id
     * @return _msgcode the code for result
     * @return _msg the msg for result
     * @return _cpt the cpt info for result
     */
    function getCptByCptId(
        string memory _cptId
    )
    public
    view
    returns (
        string memory _msgcode,
        string memory _msg,
        string memory _cpt
    )
    {
        bool bools = isCptExist(_cptId);
        if (!bools){
            return (CPT_NOT_EXIST, "cpt not exist", "");
        }
        bytes memory _cptIdByte = bytes(_cptId);
        bytes memory _cptbytes = cptMap.getValue(_cptIdByte);
        return (SUCCESS, "success", string(_cptbytes));
    }

    /**
     * Get cptList by did
     *
     * @param _did the did
     * @param page the page
     * @param size the size
     * @return _msgcode the code for result
     * @return _msg the msg for result
     * @return total the total for result
     * @return totalPage the totalPage for result
     * @return _cptlist the cpt info list for result
     */
    function getCptListByDid(
        string memory _did,
        uint32 page,
        uint32 size
    )
    public
    view
    returns (
        string memory _msgcode,
        string memory _msg,
        uint256 total,
        uint256 totalPage,
        string[] memory _cptlist
    )
    {
        bool bools = isCptListExist(_did);
        if (!bools){
            return (CPTLIST_NOT_EXIST, "cptlist not exist", 0, 0, new string[](0));
        }
        bytes memory _didBytes = bytes(_did);
        bytes[] memory didCptMapValue = didCptMap.getValue(_didBytes);
        bytes[] memory cptlists = new bytes[](didCptMapValue.length);
        for(uint256 k = 0; k < didCptMapValue.length; k++){
            cptlists[k] = cptMap.getValue(didCptMapValue[k]);
        }
        (uint256 totals, uint256 totalPages, string[] memory List) = UtilsContract.pageIndex(page, size, cptlists);
        return (SUCCESS, "success", totals, totalPages, List);
    }

    /**
     * Get all cpt
     *
     * @param page the page
     * @param size the size
     * @return _msgcode the code for result
     * @return _msg the msg for result
     * @return total the total for result
     * @return totalPage the totalPage for result
     * @return _cptlists the cpt all infos list for result
     */
    function getAllCptList(
        uint32 page,
        uint32 size
    )
    public
    view
    returns (
        string memory _msgcode,
        string memory _msg,
        uint256 total,
        uint256 totalPage,
        string[] memory _cptlists
    )
    {
        uint256 num = 0;
        uint256 k = didCptMap.iterate_start();
        bytes[] memory cptLists = new bytes[](cptNum);
        while(didCptMap.can_iterate(k)){
            bytes memory keys = didCptMap.getKeyByIndex(k);
            bytes[] memory didCptMapValues = didCptMap.getValue(keys);
            for(uint256 g = 0; g < didCptMapValues.length; g++){
                cptLists[num] = didCptMapValues[g];
                num++;
            }
            k = didCptMap.iterate_next(k);
        }
        if(cptLists.length == 0){
            return (CPT_NOT_EXIST, "cpt not exist", 0, 0, new string[](0));
        }

        (uint256 totals, uint256 totalPages, string[] memory List) = UtilsContract.pageIndex(page, size, cptLists);
        return (SUCCESS, "success", totals, totalPages, List);
    }

    /** Update cpt
     *
     * @param _did the did
     * @param _cptId the cptId
     * @param _cpt the cpt info
     * @return _msgcode the code for result
     * @return _msg the msg for result
     */
    function updateCpt(
        string memory _did,
        string memory _cptId,
        string memory _cpt
    )
    public
    onlyOwner
    returns (
        string memory _msgcode,
        string memory _msg
    )
    {

        bool bools = isCptListExist(_did);
        bool boolss = isCptExist(_cptId);
        if (!(bools && boolss)) {
            emit registerCptRetLog(CPT_NOT_EXIST, "cpt not exist");
            return (CPT_NOT_EXIST, "cpt not exist");
        }
        bytes memory _didBytes = bytes(_did);
        bytes memory _cptIdBytes = bytes(_cptId);
        bytes memory _cptBytes = bytes(_cpt);
        bytes[] memory didCpts = didCptMap.getValue(_didBytes);
        for (uint256 w = 0; w < didCpts.length; w++){
            if (UtilsContract.equalBytes(didCpts[w], _cptIdBytes)){
                cptMap.put(_cptIdBytes, _cptBytes);
                emit updateCptRetLog(SUCCESS, "success");
                return (SUCCESS, "success");
            }
        }
        emit updateCptRetLog(DID_NOT_CORRESPOND_TO_CPTID, "did not correspond to cptId");
        return (DID_NOT_CORRESPOND_TO_CPTID, "did not correspond to cptId");
    }

    /** Determine whether CptList exist, and return true if it exist
     *
     * @param _did the did
     * @return _bool the bool for result
     */
    function isCptListExist(
        string memory _did
    )
    public
    view
    returns (
        bool
    )
    {
        bytes memory key = bytes(_did);
        uint256 idx = didCptMap.index[key];
        if (idx == 0){
            return false;
        }
        return true;
    }


    /** Determine whether cpt exist, and return true if it exist
     *
     * @param _cptId the did
     * @return _bool the bool for result
     */
    function isCptExist(
        string memory _cptId
    )
    public
    view
    returns (bool)
    {
        bytes memory key = bytes(_cptId);
        uint256 idx = cptMap.index[key];
        if (idx == 0){
            return false;
        }
        return true;
    }

    /**
    Certificate revocation management
    */

    /** Upload voucher number
     *
     * @param _did the did
     * @param _credId the credId
     * @param _info the revocation voucher info
     * @return _msgcode the code for result
     * @return _msg the msg for result
     */
    function revokeCredential(
        string memory _did,
        string memory _credId,
        string memory _info
    )
    public
    onlyOwner
    returns (
        string memory _msgcode,
        string memory _msg
    )
    {
        bytes memory didBytes = bytes(_did);
        bytes memory credIdBytes = bytes(_credId);
        bytes memory infoBytes = bytes(_info);
        bytes[] memory credIdList = new bytes[](1);
        bool boolss;
        bool bools = isCreListExist(_did);
        if (bools){
            boolss = isCreIdInfoExist(_credId);
            if (boolss){
                emit revokeCredentialRetLog(VOUCHER_HAS_BEEN_REVOKED, "Voucher has been revoked");
                return (VOUCHER_HAS_BEEN_REVOKED, "Voucher has been revoked");
            }
            bytes[] memory oldCredIdList = didCreIdListMap.getValue(didBytes);
            bytes[] memory newCredIdList = new bytes[](oldCredIdList.length+1);
            for (uint256 x = 0; x < oldCredIdList.length; x++){
                newCredIdList[x] = oldCredIdList[x];
            }
            newCredIdList[newCredIdList.length-1] = credIdBytes;
            didCreIdListMap.put(didBytes, newCredIdList);
            credIdInfoMap.put(credIdBytes, infoBytes);
        }else{
            boolss = isCreIdInfoExist(_credId);
            if (boolss){
                emit revokeCredentialRetLog(VOUCHER_HAS_BEEN_REVOKED, "Voucher has been revoked");
                return (VOUCHER_HAS_BEEN_REVOKED, "Voucher has been revoked");
            }
            credIdList[0] = credIdBytes;
            didCreIdListMap.put(didBytes, credIdList);
            credIdInfoMap.put(credIdBytes, infoBytes);
        }
        emit revokeCredentialRetLog(SUCCESS, "success");
        return (SUCCESS, "success");
    }

    /** Get the revocation list based on did
     *
     * @param _did the did
     * @param page the page
     * @param size the size
     * @return _msgcode the code for result
     * @return _msg the msg for result
     * @return total the total for result
     * @return totalPage the totalPage for result
     * @return _list did the corresponding revocation list for result
     */
    function getRevokedCredList(
        string memory _did,
        uint32 page,
        uint32 size
    )
    public
    view
    returns (
        string memory _msgcode,
        string memory _msg,
        uint256 total,
        uint256 totalPage,
        string[] memory _list
    )
    {

        bytes memory _didBytes = bytes(_did);
        bool bools = isCreListExist(_did);
        if (!bools){
            return (CREDLIST_NOT_EXIST, "credList not exist", 0, 0,new string[](0));
        }
        bytes[] memory lists = didCreIdListMap.getValue(_didBytes);
        (uint256 totals, uint256 totalPages, string[] memory List) = UtilsContract.pageIndex(page, size, lists);
        return (SUCCESS, "success", totals, totalPages, List);
    }

    /** Obtain the voucher based on the did and the number of the revocation voucher
     *
     * @param _did the did
     * @param _credId the credId
     * @return _msgcode the code for result
     * @return _msg the msg for result
     * @return _info the revocation voucher info for result
     */
    function getRevokedCred(
        string memory _did,
        string memory _credId
    )
    public
    view
    returns (
        string memory _msgcode,
        string memory _msg,
        string memory _info
    )
    {
        bytes memory _didBytes = bytes(_did);
        bytes memory _credIdBytes = bytes(_credId);
        bool bools = isCreListExist(_did);
        if (!bools){
            return (CREDLIST_NOT_EXIST, "credList not exist", "");
        }
        bool boolss = isCreIdInfoExist(_credId);
        if (!boolss){
            return (CREDINFO_NOT_EXIST, "credInfo_not_exist", "");
        }
        bytes[] memory listss = didCreIdListMap.getValue(_didBytes);
        for(uint256 j = 0; j < listss.length; j++){
            if (UtilsContract.equalBytes(listss[j], _credIdBytes)){
                bytes memory info  = credIdInfoMap.getValue(_credIdBytes);
                return (SUCCESS, "success", string(info));
            }
        }
        return (CREDINFO_NOT_EXIST, "credInfo_not_exist", "");
    }

    /** Determine whether credIdList exist, and return true if it exist
     *
     * @param _id the id
     * @return _bool the bool for result
     */
    function isCreListExist(
        string memory _id
    )
    public
    view
    returns (
        bool
    )
    {
        bytes memory key = bytes(_id);
        uint256 idx = didCreIdListMap.index[key];
        if (idx == 0){
            return false;
        }
        return true;
    }

    /** Determine whether credIdInfo exist, and return true if it exist
     *
     * @param _id the id
     * @return _bool the bool for result
     */
    function isCreIdInfoExist(
        string memory _id
    )
    public
    view
    returns (
        bool
    )
    {
        bytes memory key = bytes(_id);
        uint256 idx = credIdInfoMap.index[key];
        if (idx == 0){
            return false;
        }
        return true;
    }
}
