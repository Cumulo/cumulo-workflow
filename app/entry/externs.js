
/**
 BEGIN_NODE_INCLUDE
 var fs = require('fs');
 END_NODE_INCLUDE
 */

var fs = {};

/**
 * @param {string} filename
 * @param {string|{encoding:(string|undefined),flag:(string|undefined)}=} encodingOrOptions
 * @return {string|buffer.Buffer}
 * @nosideeffects
 */
fs.readFileSync = function(filename, encodingOrOptions) {};

/**
 * @param {string} filename
 * @param {*} data
 * @param {string|{encoding:(string|undefined),mode:(number|undefined),flag:(string|undefined)}|function(string)=} encodingOrOptions
 */
fs.writeFileSync = function(filename, data, encodingOrOptions) {};


/**
 * @constructor
 * @extends events.EventEmitter
 */
var process = function() {};

/**
 * @type {Object.<string,string>}
 */
process.env;
