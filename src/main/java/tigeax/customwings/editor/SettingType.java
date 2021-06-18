package tigeax.customwings.editor;

/* 
 * The different settings that can be changed trough the editor GUI's
 * chatInput and inventoryInput tell if the setting gets it's value from a chat input or an inventory item click
 */

public enum SettingType {
	
	VIEWDISTANCE(true, false),
	MAINGUINAME(true, false),
	MAINGUISIZE(false, false),
	REMOVEWINGNAME(true, false),
	REMOVEWINGMATERIAL(false, true),
	REMOVEWINGSLOT(false, false),
	HIDEWINGTOGGLENAMEON(true, false),
	HIDEWINGTOGGLENAMEOFF(true, false),
	HIDEWINGTOGGLEMATERIALON(false, true),
	HIDEWINGTOGGLEMATERIALOFF(false, true),
	HIDEWINGTOGGLESLOT(false, false),
	EDITORGUINAME(true, false),
	EDITORMAINSETTINGSSLOT(false, false),
	WINGSHOWWHENMOVING(false, false),
	WINGWHITELISTEDWORLDS(true, false),
	WINGHIDEINGUI(false, false),
	WINGGUINAME(true, false),
	WINGGUIMATERIAL(false, true),
	WINGGUISLOT(false, false),
	WINGGUILOREHWENEQUIPPED(true, false),
	WINGGUILOREWHENUNEQUIPPED(true, false),
	WINGGUILOREWHENNOPERMISSION(true, false),
	WINGSTARTVERTICAL(false, false),
	WINGSTARTHORIZONTAL(false, false),
	WINGDISTANCEBETWEENPARTICLES(false, false),
	WINGTIMER(false, false),
	WINGANIMATION(false, false),
	WINGFLAPSPEED(false, false),
	WINGSTARTOFFSET(false, false),
	WINGSTOPOFFSET(false, false),
	WINGPARTICLEPARTICLE(false, false),
	WINGPARTICLEDISTANCE(false, false),
	WINGPARTICLEHEIGHT(false, false),
	WINGPARTICLEANGLE(false, false),
	WINGPARTICLESPEED(false, false),
	WINGPARTICLEBLOCKTYPE(false, true),
	WINGPARTICLECOLOR(true, false);
	
	private boolean chatInput;
	private boolean inventroyInput;
	
	SettingType(final boolean chatInput, final boolean inventroyInput) {
		this.chatInput = chatInput;
		this.inventroyInput = inventroyInput;
	}
	
	public boolean isChatInputSetting() {
		return chatInput;
	}
	
	public boolean isInventoryInputSetting() {
		return inventroyInput;
	}
}
