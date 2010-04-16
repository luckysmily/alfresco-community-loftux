/**
 * Copyright (C) 2005-2010 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
 
/**
 * Repository DocumentList Toolbar component.
 * 
 * @namespace Alfresco
 * @class Alfresco.RepositoryDocListToolbar
 */
(function()
{
   /**
    * YUI Library aliases
    */
   var Dom = YAHOO.util.Dom,
      Event = YAHOO.util.Event,
      Element = YAHOO.util.Element;

   /**
    * Alfresco Slingshot aliases
    */
   var $html = Alfresco.util.encodeHTML,
      $combine = Alfresco.util.combinePaths;

   /**
    * RepositoryDocListToolbar constructor.
    * 
    * @param {String} htmlId The HTML id of the parent element
    * @return {Alfresco.RepositoryDocListToolbar} The new DocListToolbar instance
    * @constructor
    */
   Alfresco.RepositoryDocListToolbar = function(htmlId)
   {
      return Alfresco.RepositoryDocListToolbar.superclass.constructor.call(this, htmlId);
   };

   /**
    * Extend Alfresco.DocListToolbar
    */
   YAHOO.extend(Alfresco.RepositoryDocListToolbar, Alfresco.DocListToolbar);

   /**
    * Augment prototype with main class implementation, ensuring overwrite is enabled
    */
   YAHOO.lang.augmentObject(Alfresco.RepositoryDocListToolbar.prototype,
   {
      /**
       * Set multiple initialization options at once.
       *
       * @method setOptions
       * @override
       * @param obj {object} Object literal specifying a set of options
       * @return {Alfresco.RepositoryDocListToolbar} returns 'this' for method chaining
       */
      setOptions: function RDLTB_setOptions(obj)
      {
         return Alfresco.RepositoryDocListToolbar.superclass.setOptions.call(this, YAHOO.lang.merge(
         {
            workingMode: Alfresco.doclib.MODE_REPOSITORY
         }, obj));
      },


      /**
       * YUI WIDGET EVENT HANDLERS
       * Handlers for standard events fired from YUI widgets, e.g. "click"
       */

      /**
       * File Upload button click handler
       *
       * @method onFileUpload
       * @param e {object} DomEvent
       * @param p_obj {object} Object passed back from addListener method
       */
      onFileUpload: function RDLTB_onFileUpload(e, p_obj)
      {
         if (this.fileUpload === null)
         {
            this.fileUpload = Alfresco.getFileUploadInstance(); 
         }
         
         // Show uploader for multiple files
         var multiUploadConfig =
         {
            destination: this.modules.docList.doclistMetadata.parent.nodeRef,
            filter: [],
            mode: this.fileUpload.MODE_MULTI_UPLOAD,
            thumbnails: "doclib",
            onFileUploadComplete:
            {
               fn: this.onFileUploadComplete,
               scope: this
            }
         };
         this.fileUpload.show(multiUploadConfig);
      },
      
      /**
       * File Upload complete event handler
       *
       * @method onFileUploadComplete
       * @param complete {object} Object literal containing details of successful and failed uploads
       */
      onFileUploadComplete: function RDLTB_onFileUploadComplete(complete)
      {
         // Overridden so activity doesn't get posted
      },


      /**
       * PRIVATE FUNCTIONS
       */
      
      /**
       * Generates the HTML mark-up for the RSS feed link
       *
       * @method _generateRSSFeedUrl
       * @private
       */
      _generateRSSFeedUrl: function RDLTB__generateRSSFeedUrl()
      {
         if (this.widgets.rssFeed && this.modules.docList)
         {
            var params = YAHOO.lang.substitute("{type}/node/{nodeRef}{path}",
            {
               type: this.modules.docList.options.showFolders ? "all" : "documents",
               nodeRef: this.options.nodeRef.uri,
               path: Alfresco.util.encodeURIPath(this.currentPath)
            });

            params += "?filter=" + encodeURIComponent(this.currentFilter.filterId);
            if (this.currentFilter.filterData)
            {
               params += "&filterData=" + encodeURIComponent(this.currentFilter.filterData);             
            }
            params += "&libraryRoot=" + this.options.nodeRef.toString();
            params += "&format=rss";
            
            this.widgets.rssFeed.set("href", Alfresco.constants.URL_FEEDSERVICECONTEXT + "components/documentlibrary/feed/" + params);
         }
      }
   }, true);
})();