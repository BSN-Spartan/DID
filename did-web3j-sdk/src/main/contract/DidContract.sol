pragma solidity ^0.5.0;

import "./LibBytesMap.sol";
import "./Ownable.sol";

contract DidContract is Ownable{

    using LibBytesMap for LibBytesMap.Map;
    LibBytesMap.Map private didMap;

    /**
     * Error code
     */
    string constant private SUCCESS = "0000";
    string constant private DID_NOT_EXIST = "2011";
    string constant private DID_ALREADY_EXIST = "2012";

    event createDidRetLog(
        string _msgcode,
        string _msg
    );

    event updateDidAuthRetLog(
        string _msgcode,
        string _msg
    );

    /**
     * Create did
     *
     * @param _did the did
     * @param _didDocument the didDocument
     * @return _msgcode the code for result
     * @return _msg the msg for result
     */
    function createDid(
        string memory _did,
        string memory _didDocument
    )
    public
    onlyOwner
    returns (
        string memory _msgcode,
        string memory _msg
    )
    {
        bool bools = isDidExist(_did);
        if (bools){
            emit createDidRetLog(DID_ALREADY_EXIST, "did already exist");
            return (DID_ALREADY_EXIST, "did already exist");
        }
        bytes memory _didBytes = bytes(_did);
        bytes memory _didDocumentBytes = bytes(_didDocument);
        didMap.put(_didBytes, _didDocumentBytes);
        emit createDidRetLog(SUCCESS, "success");
        return (SUCCESS, "success");
    }

    /**
     * Get did document
     *
     * @param _did the did
     * @return _msgcode the code for result
     * @return _msg the msg for result
     * @return _didDocument the didDocument for result
     */
    function getDocument(
        string memory _did
    )
    public
    view
    returns (
        string memory _msgcode,
        string memory _msg,
        string memory _didDocument
    )
    {
        bool bools = isDidExist(_did);
        if (!bools){
            return (DID_NOT_EXIST, "did not exist", "");
        }
        bytes memory _didBytes = bytes(_did);
        bytes memory _didDocumentBytes = didMap.getValue(_didBytes);
        return (SUCCESS, "success", string(_didDocumentBytes));
    }

    /**
     * Update did key
     *
     * @param _did the did
     * @param _didDocument the didDocument
     * @return _msgcode the code for result
     * @return _msg the msg for result
     */
    function updateDidAuth(
        string memory _did,
        string memory _didDocument
    )
    public
    onlyOwner
    returns (
        string memory _msgcode,
        string memory _msg
    )
    {
        bool bools = isDidExist(_did);
        if (!bools){
            emit updateDidAuthRetLog(DID_NOT_EXIST, "did not exist");
            return (DID_NOT_EXIST, "did not exist");
        }
        bytes memory _didBytes = bytes(_did);
        bytes memory _didDocumentBytes = bytes(_didDocument);
        didMap.put(_didBytes, _didDocumentBytes);
        emit updateDidAuthRetLog(SUCCESS, "success");
        return (SUCCESS, "success");
    }

    /**
     * Judge whether did exist, return true if it exist
     *
     * @param _did the did
     * @return bool the bool for result
     */
    function isDidExist(
        string memory _did
    )
    public
    view
    returns (
        bool
    )
    {
        bytes memory key = bytes(_did);
        uint256 idx = didMap.index[key];
        if (idx == 0){
            return false;
        }
        return true;
    }
}
