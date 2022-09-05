pragma solidity ^0.5.0;

import "./LibBytesMap.sol";
import "./Ownable.sol";
import "./UtilsContract.sol";
pragma experimental ABIEncoderV2;


contract IssuerContract is Ownable{

    using LibBytesMap for LibBytesMap.Map;
    LibBytesMap.Map private issuerMap;

    /**
     * Error code
     */
    string constant private SUCCESS = "0000";
    string constant private ISSUER_NOT_EXIST = "2021";
    string constant private ISSUER_ALREADY_EXIST = "2022";

    event createIssuerRetLog(
        string msgcode,
        string msg
    );

    /**
     * Create issuer
     *
     * @param _did the did
     * @param _issuerInfo the issuer info
     * @return _msgcode the code for result
     * @return _msg the msg for result
     */
    function createIssuer(
        string memory _did,
        string memory _issuerInfo
    )
    public
    onlyOwner
    returns (
        string memory _msgcode,
        string memory _msg
    )
    {
        bool bools = isIssuerExist(_did);
        if (bools){
            emit createIssuerRetLog(ISSUER_ALREADY_EXIST, "issuer already exist");
            return (ISSUER_ALREADY_EXIST, "issuer already exist");
        }
        bytes memory _didBytes = bytes(_did);
        bytes memory _issuerInfoBytes = bytes(_issuerInfo);
        issuerMap.put(_didBytes, _issuerInfoBytes);
        emit createIssuerRetLog(SUCCESS, "success");
        return (SUCCESS, "success");
    }

    /**
     * Get issuer by did
     *
     * @param _did the did
     * @return _msgcode the code for result
     * @return _msg the msg for result
     * @return _issuerInfo the issuer info for result
     */
    function getIssuerByDid(
        string memory _did
    )
    public
    view
    returns (
        string memory _msgcode,
        string memory _msg,
        string memory _issuerInfo
    )
    {
        bool bools = isIssuerExist(_did);
        if (!bools){
            return (ISSUER_NOT_EXIST, "issuer not exist", "");
        }
        bytes memory _didBytes = bytes(_did);
        bytes memory _issuerInfoBytes = issuerMap.getValue(_didBytes);
        return (SUCCESS, "success", string(_issuerInfoBytes));
    }

    /**
     * Get all issuer info
     *
     * @param page the page
     * @param size the size
     * @return _msgcode the code for result
     * @return _msg the msg for result
     * @return total the total for result
     * @return totalPage the totalPage for result
     * @return _issuerLists the all issuer info list for result
     */
    function getAllIssuerInfo(
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
        string[] memory _issuerLists
    )
    {
        bytes[] memory issuerMapValues = issuerMap.values;
        if(issuerMapValues.length == 0){
            return (ISSUER_NOT_EXIST, "issuer not exist", 0, 0, new string[](0));
        }
        (uint256 totals, uint256 totalPages, string[] memory issuerList) = UtilsContract.pageIndex(page, size, issuerMapValues);
        return (SUCCESS, "success", totals, totalPages, issuerList);
    }

    /**
     * Judge whether issuer exists, return true if it exist
     *
     * @param _did the did
     * @return bool the bool for result
     */
    function isIssuerExist(
        string memory _did
    )
    public
    view
    returns (
        bool
    )
    {
        bytes memory key = bytes(_did);
        uint256 idx = issuerMap.index[key];
        if (idx == 0){
            return false;
        }
        return true;
    }
}

