package com.github.mei3am.test.models.response

import com.google.gson.annotations.SerializedName

data class ContentDetailsResponse(

	@field:SerializedName("Status")
	val status: Int? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Result")
	val result: ContentResult? = null
)

data class Type(

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Id")
	val id: Int? = null
)

data class FilesItem(

	@field:SerializedName("Path")
	val path: String? = null,

	@field:SerializedName("UniqueId")
	val uniqueId: Int? = null,

	@field:SerializedName("Type")
	val type: Int? = null,

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Quality")
	val quality: Int? = null,

	@field:SerializedName("Size")
	val size: Long? = null,

	@field:SerializedName("FileExtension")
	val fileExtension: String? = null,

	@field:SerializedName("Times")
	val times: List<TimesItem?>? = null,

	@field:SerializedName("ViewCount")
	val viewCount: Int? = null,

	@field:SerializedName("Duration")
	val duration: Int? = null,

	@field:SerializedName("CdnType")
	val cdnType: Int? = null,

	@field:SerializedName("Thumbnail")
	val thumbnail: String? = null
)

data class CategoriesItem(

	@field:SerializedName("CategoryID")
	val categoryID: Int? = null,

	@field:SerializedName("ParentID")
	val parentID: Int? = null,

	@field:SerializedName("ZoneID")
	val zoneID: Int? = null,

	@field:SerializedName("Title")
	val title: String? = null
)

data class AttachmentQualityItem(

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("ID")
	val iD: Int? = null
)

data class PropertiesItem(

	@field:SerializedName("Value")
	val value: String? = null,

	@field:SerializedName("PropertyId")
	val propertyId: Int? = null,

	@field:SerializedName("Name")
	val name: String? = null
)

data class TagListItem(

	@field:SerializedName("BackgroundImage")
	val backgroundImage: String? = null,

	@field:SerializedName("Description")
	val description: Any? = null,

	@field:SerializedName("SectionPriority")
	val sectionPriority: Int? = null,

	@field:SerializedName("TagId")
	val tagId: Int? = null,

	@field:SerializedName("IsSelected")
	val isSelected: Boolean? = null,

	@field:SerializedName("Image")
	val image: String? = null,

	@field:SerializedName("IsFollowed")
	val isFollowed: Any? = null,

	@field:SerializedName("Name")
	val name: String? = null
)

data class CommentTemplateListItem(

	@field:SerializedName("TypeId")
	val typeId: Int? = null,

	@field:SerializedName("TypeTitle")
	val typeTitle: String? = null,

	@field:SerializedName("Id")
	val id: Int? = null,

	@field:SerializedName("Body")
	val body: String? = null
)

data class TimesItem(

	@field:SerializedName("Type")
	val type: Type? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Time")
	val time: Int? = null,

	@field:SerializedName("Id")
	val id: Int? = null
)

data class AttachmentListItem(

	@field:SerializedName("IsDubbed")
	val isDubbed: Boolean? = null,

	@field:SerializedName("IsFree")
	val isFree: Boolean? = null,

	@field:SerializedName("Type")
	val type: Int? = null,

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("PartNo")
	val partNo: Int? = null,

	@field:SerializedName("FileExtension")
	val fileExtension: String? = null,

	@field:SerializedName("LastVisitEndSecond")
	val lastVisitEndSecond: Int? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("ViewCount")
	val viewCount: Int? = null,

	@field:SerializedName("Available")
	val available: Boolean? = null,

	@field:SerializedName("Duration")
	val duration: Int? = null,

	@field:SerializedName("Files")
	val files: List<FilesItem?>? = null
)

data class ContentResult(

	@field:SerializedName("CommentPermission")
	val commentPermission: Int? = null,

	@field:SerializedName("ShortURL")
	val shortURL: String? = null,

	@field:SerializedName("ZoneID")
	val zoneID: Int? = null,

	@field:SerializedName("LandscapeImage9X4")
	val landscapeImage9X4: String? = null,

	@field:SerializedName("DirectorList")
	val directorList: List<Any?>? = null,

	@field:SerializedName("NarratorList")
	val narratorList: List<Any?>? = null,

	@field:SerializedName("CommentTemplateList")
	val commentTemplateList: List<CommentTemplateListItem?>? = null,

	@field:SerializedName("DisLikeCount")
	val disLikeCount: Int? = null,

	@field:SerializedName("StarsList")
	val starsList: List<StarsListItem?>? = null,

	@field:SerializedName("ThumbImage")
	val thumbImage: String? = null,

	@field:SerializedName("Properties")
	val properties: List<PropertiesItem?>? = null,

	@field:SerializedName("LikeStatus")
	val likeStatus: Boolean? = null,

	@field:SerializedName("CreateDate")
	val createDate: Int? = null,

	@field:SerializedName("UpdateDate")
	val updateDate: Int? = null,

	@field:SerializedName("NumberOfSeason")
	val numberOfSeason: Int? = null,

	@field:SerializedName("TagList")
	val tagList: List<TagListItem?>? = null,

	@field:SerializedName("ContentID")
	val contentID: Int? = null,

	@field:SerializedName("Available")
	val available: Boolean? = null,

	@field:SerializedName("PortraitImage")
	val portraitImage: String? = null,

	@field:SerializedName("DisLikeStatus")
	val disLikeStatus: Boolean? = null,

	@field:SerializedName("Body")
	val body: String? = null,

	@field:SerializedName("FavoriteStatus")
	val favoriteStatus: Boolean? = null,

	@field:SerializedName("SourceSiteLogoUrl")
	val sourceSiteLogoUrl: String? = null,

	@field:SerializedName("Categories")
	val categories: List<CategoriesItem?>? = null,

	@field:SerializedName("AttachmentQuality")
	val attachmentQuality: List<AttachmentQualityItem?>? = null,

	@field:SerializedName("ViewCount")
	val viewCount: Int? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("SourceSiteTitle")
	val sourceSiteTitle: String? = null,

	@field:SerializedName("PortraitImage9X11")
	val portraitImage9X11: String? = null,

	@field:SerializedName("EnglishBody")
	val englishBody: String? = null,

	@field:SerializedName("AccessLevelTypeID")
	val accessLevelTypeID: Int? = null,

	@field:SerializedName("TranslatorList")
	val translatorList: List<Any?>? = null,

	@field:SerializedName("CollectionImage")
	val collectionImage: String? = null,

	@field:SerializedName("Type")
	val type: Int? = null,

	@field:SerializedName("Price")
	val price: Int? = null,

	@field:SerializedName("AuthorList")
	val authorList: List<Any?>? = null,

	@field:SerializedName("WikiList")
	val wikiList: List<Any?>? = null,

	@field:SerializedName("Summary")
	val summary: String? = null,

	@field:SerializedName("CommentCount")
	val commentCount: Int? = null,

	@field:SerializedName("LandscapeImage")
	val landscapeImage: String? = null,

	@field:SerializedName("AttachmentList")
	val attachmentList: List<AttachmentListItem?>? = null,

	@field:SerializedName("AssetDomain")
	val assetDomain: String? = null,

	@field:SerializedName("LikeCount")
	val likeCount: Int? = null,

	@field:SerializedName("SMSOperationCode")
	val sMSOperationCode: String? = null,

	@field:SerializedName("SourceSiteWebUrl")
	val sourceSiteWebUrl: String? = null
)

data class StarsListItem(

	@field:SerializedName("ParentId")
	val parentId: Any? = null,

	@field:SerializedName("NickName")
	val nickName: Any? = null,

	@field:SerializedName("LikeStatus")
	val likeStatus: Boolean? = null,

	@field:SerializedName("Nationality")
	val nationality: Any? = null,

	@field:SerializedName("DeathDate")
	val deathDate: Int? = null,

	@field:SerializedName("OtherInfo")
	val otherInfo: Any? = null,

	@field:SerializedName("PersianName")
	val persianName: String? = null,

	@field:SerializedName("AvatarUrl")
	val avatarUrl: String? = null,

	@field:SerializedName("ContentID")
	val contentID: Int? = null,

	@field:SerializedName("PersonRoleId")
	val personRoleId: Int? = null,

	@field:SerializedName("ID")
	val iD: Int? = null,

	@field:SerializedName("ContentPersonId")
	val contentPersonId: Int? = null,

	@field:SerializedName("Children")
	val children: List<Any?>? = null,

	@field:SerializedName("BirthDate")
	val birthDate: Int? = null,

	@field:SerializedName("EnglishName")
	val englishName: String? = null
)
